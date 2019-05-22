package hamburg.remme.lwjgl.geometry

import org.lwjgl.opengl.GL15.*

class Buffer(private val type: Int) {

    companion object {

        fun free(type: Int) {
            glBindBuffer(type, 0)
        }

    }

    private val pointer = glGenBuffers()

    constructor(type: Int, data: FloatArray) : this(type) {
        glBufferData(type, data, GL_STATIC_DRAW)
    }

    constructor(type: Int, data: ShortArray) : this(type) {
        glBufferData(type, data, GL_STATIC_DRAW)
    }

    init {
        glBindBuffer(type, pointer)
    }

    // TODO: needed? we usually do not use buffers alone
    fun bind() {
        glBindBuffer(type, pointer)
    }

    fun update(data: FloatArray) {
        glBufferSubData(type, 0, data)
    }

    fun update(data: ShortArray) {
        glBufferSubData(type, 0, data)
    }

    fun delete() {
        glDeleteBuffers(pointer)
    }

}
