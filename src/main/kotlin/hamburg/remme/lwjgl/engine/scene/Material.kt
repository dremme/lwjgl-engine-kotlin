package hamburg.remme.lwjgl.engine.scene

import hamburg.remme.lwjgl.engine.shader.ShaderProgram
import hamburg.remme.lwjgl.engine.texture.Texture
import org.joml.Vector3f

class Material(var shader: ShaderProgram, var texture: Texture? = null) {

    val tint: Vector3f = Vector3f(1f)
    val hasTexture: Boolean get() = texture != null

}
