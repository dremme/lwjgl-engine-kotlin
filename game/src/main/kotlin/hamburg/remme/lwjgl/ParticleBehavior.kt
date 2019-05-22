package hamburg.remme.lwjgl

import hamburg.remme.lwjgl.input.Button
import org.joml.Vector3f

class ParticleBehavior(
    private val drag: Float,
    private val acceleration: Float = 0f
) : Behavior<ParticleGame>() {

    private val velocity = Vector3f()
    private val force = Vector3f()

    override fun update() {
        val position = gameObject.position
        val mousePosition = window.getMousePosition()
        val leftButton = window.getButton(Button.LEFT)
        val rightButton = window.getButton(Button.RIGHT)

        if (leftButton || rightButton) {
            val dirX = mousePosition.x() - position.x
            val dirY = mousePosition.y() - position.y
            force.set(dirX, dirY, 0f).normalize().mul(acceleration)
            velocity.add(if (rightButton) force.negate() else force)
        }

        position.add(velocity) // acceleration
        velocity.mul(drag) // drag

        keepInsideBounds()
    }

    private fun keepInsideBounds() {
        val position = gameObject.position
        val width = window.width
        val height = window.height
        if (position.x < 0 && velocity.x < 0 || position.x > width && velocity.x > 0) velocity.x = -velocity.x
        if (position.y < 0 && velocity.y < 0 || position.y > height && velocity.y > 0) velocity.y = -velocity.y
    }

}
