package hamburg.remme.lwjgl.engine.shader

import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL20.*

abstract class Shader(type: Int, source: String) {

    internal val pointer = glCreateShader(type)

    init {
        glShaderSource(pointer, source)
        glCompileShader(pointer)

        val infoLog = glGetShaderInfoLog(pointer, glGetShaderi(pointer, GL_INFO_LOG_LENGTH))
        if (glGetShaderi(pointer, GL_COMPILE_STATUS) == GL_FALSE) {
            delete()
            throw RuntimeException("Error compiling shader:\n$infoLog")
        }
    }

    fun delete() {
        glDeleteShader(pointer)
    }

}
