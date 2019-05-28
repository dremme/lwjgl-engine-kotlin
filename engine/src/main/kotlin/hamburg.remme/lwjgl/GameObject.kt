package hamburg.remme.lwjgl

import hamburg.remme.lwjgl.scene.Model
import org.joml.Quaternionf
import org.joml.Vector3f

class GameObject<T : Application<T>>(val model: Model, vararg val behaviors: Behavior<T>) {

    val position: Vector3f = Vector3f()
    val scale: Vector3f = Vector3f(1f)
    val rotation: Quaternionf = Quaternionf()

}
