package hamburg.remme.lwjgl

import hamburg.remme.lwjgl.scene.Model
import org.joml.Quaternionf
import org.joml.Vector3f

class GameObject<T : Application<T>>(var model: Model? = null) {

    val behaviors: MutableSet<Behavior<T>> = mutableSetOf()
    val position: Vector3f = Vector3f()
    val scale: Vector3f = Vector3f(1f)
    val rotation: Quaternionf = Quaternionf()
    val hasModel: Boolean get() = model != null

}
