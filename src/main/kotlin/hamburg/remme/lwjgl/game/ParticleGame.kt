package hamburg.remme.lwjgl.game

import hamburg.remme.lwjgl.engine.Application
import hamburg.remme.lwjgl.engine.GameObject
import hamburg.remme.lwjgl.engine.geometry.Quad
import hamburg.remme.lwjgl.engine.scene.Material
import hamburg.remme.lwjgl.engine.scene.Model
import hamburg.remme.lwjgl.engine.shader.createShader
import org.joml.Vector3f
import kotlin.random.Random

private const val WIDTH = 1280
private const val HEIGHT = 720
private const val TITLE = "Particle Game"

fun main() {
    ParticleGame().launch(WIDTH, HEIGHT, TITLE)
}

class ParticleGame : Application<ParticleGame>() {

    override fun getThis(): ParticleGame = this

    override fun start() {
        // Init engine
        window.vSync = false

        // Init mesh & shader
        val mesh = Quad()
        val shader = createShader("/standard.vert.glsl", "/standard.frag.glsl")

        // Add some objects to render queue
        (1 until 10_000).forEach { _ ->
            val m = Material(shader)
            randomColor(m)

            val o = GameObject<ParticleGame>(Model(mesh, m))
            o.behaviors += ParticleBehavior(0.98f, 1f)
            o.scale.set(1.5f, 1.5f, 1f)
            positionObject(o)

            add(o)
        }

        positionCamera()
    }

    override fun stop(): Unit = Unit

    private fun randomColor(material: Material) {
        material.tint.set(
            Random.nextFloat() * 0.5f + 0.5f,
            Random.nextFloat() * 0.5f + 0.5f,
            Random.nextFloat() * 0.5f + 0.5f
        )
    }

    private fun positionObject(gameObject: GameObject<*>) {
        gameObject.position.set(
            Random.nextFloat() * window.width,
            Random.nextFloat() * window.height,
            0f
        )
        gameObject.rotation.rotateZ(Random.nextFloat() * 90f * 0.01745f)
    }

    private fun positionCamera() {
        renderer.camera
            .setOrtho(
                0f,
                window.width.toFloat(),
                window.height.toFloat(),
                0f,
                .1f,
                100f
            )
            .lookAt(
                Vector3f(0f, 0f, 4f),
                Vector3f(0f, 0f, 0f),
                Vector3f(0f, 1f, 0f)
            )
    }

}
