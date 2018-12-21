package hamburg.remme.lwjgl.engine.shader

import org.joml.Matrix4fc
import org.joml.Vector3fc
import org.lwjgl.BufferUtils.createFloatBuffer
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL20.*

class ShaderProgram(private val vertexShader: VertexShader, private val fragmentShader: FragmentShader) {

    companion object {
        private val M4_BUFFER = createFloatBuffer(16)
    }

    private val pointer = glCreateProgram()
    private val uniformLocationCache = mutableMapOf<String, Int>()
    // TODO: maybe a set cache with hash codes is good enough and faster
    private val uniformValueCache = mutableMapOf<String, Int>()

    init {
        glAttachShader(pointer, vertexShader.pointer)
        glAttachShader(pointer, fragmentShader.pointer)
        glLinkProgram(pointer)

        val infoLog = glGetProgramInfoLog(pointer, glGetProgrami(pointer, GL_INFO_LOG_LENGTH))
        if (glGetProgrami(pointer, GL_LINK_STATUS) == GL_FALSE) {
            delete()
            throw RuntimeException("Error linking program:\n$infoLog")
        }
    }

    fun use() {
        glUseProgram(pointer)
    }

    fun delete() {
        glDetachShader(pointer, vertexShader.pointer)
        glDetachShader(pointer, fragmentShader.pointer)
        glDeleteProgram(pointer)
        vertexShader.delete()
        fragmentShader.delete()
    }

    operator fun set(uniform: String, value: Boolean) {
        set(uniform, if (value) 1 else 0)
    }

    // TODO: maybe use uniform objects
    operator fun set(uniform: String, value: Int) {
        if (invalidateUniformCache(uniform, value)) {
            glUniform1i(getUniformLocation(uniform), value)
        }
    }

    // TODO: maybe use uniform objects
    operator fun set(uniform: String, value: Vector3fc) {
        if (invalidateUniformCache(uniform, value)) {
            glUniform3f(getUniformLocation(uniform), value.x(), value.y(), value.z())
        }
    }

    // TODO: maybe use uniform objects
    operator fun set(uniform: String, value: Matrix4fc) {
        if (invalidateUniformCache(uniform, value)) {
            // TODO: is FloatBuffer faster than array?
            glUniformMatrix4fv(getUniformLocation(uniform), false, value.get(M4_BUFFER))
        }
    }

    private fun invalidateUniformCache(uniform: String, value: Int): Boolean {
        if (!uniformValueCache.containsKey(uniform)) return true
        if (value == uniformValueCache[uniform]) return false
        uniformValueCache[uniform] = value
        return true
    }

    private fun invalidateUniformCache(uniform: String, value: Any): Boolean {
        if (!uniformValueCache.containsKey(uniform)) return true
        if (value.hashCode() == uniformValueCache[uniform].hashCode()) return false
        uniformValueCache[uniform] = value.hashCode()
        return true
    }

    private fun getUniformLocation(uniform: String): Int {
        var location: Int? = uniformLocationCache[uniform]
        if (location != null) return location
        location = glGetUniformLocation(pointer, uniform)
        uniformLocationCache[uniform] = location
        return location
    }

}
