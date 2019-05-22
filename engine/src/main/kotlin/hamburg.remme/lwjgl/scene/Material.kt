package hamburg.remme.lwjgl.scene

import hamburg.remme.lwjgl.shader.ShaderProgram
import hamburg.remme.lwjgl.texture.Texture
import org.joml.Vector3f

class Material(var shader: ShaderProgram, var texture: Texture? = null) {

    val tint: Vector3f = Vector3f(1f)
    val hasTexture: Boolean get() = texture != null

}
