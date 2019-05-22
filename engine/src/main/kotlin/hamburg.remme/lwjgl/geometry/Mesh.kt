package hamburg.remme.lwjgl.geometry

import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.*

open class Mesh(data: FloatArray, indices: ShortArray) {

    companion object {

        fun free() {
            glBindVertexArray(0)
            Buffer.free(GL_ARRAY_BUFFER) // TODO: needed?
            Buffer.free(GL_ELEMENT_ARRAY_BUFFER) // TODO: needed?
        }

    }

    protected val size: Int = indices.size
    private val pointer = glGenVertexArrays()
    private val vertexBuffer: Buffer
    private val indexBuffer: Buffer

    init {
        glBindVertexArray(pointer)
        vertexBuffer = Buffer(GL_ARRAY_BUFFER, data)
        indexBuffer = Buffer(GL_ELEMENT_ARRAY_BUFFER, indices)
        // VAO remembers the bound buffer

        glEnableVertexAttribArray(0)
        glVertexAttribPointer(
            0,
            3,
            GL_FLOAT,
            false,
            0,
            0
        )
    }

    open fun bind() {
        glBindVertexArray(pointer)
    }

    open fun draw(drawMode: Int) {
        glDrawElements(
            drawMode,
            size,
            GL_UNSIGNED_SHORT,
            0
        )
    }

    open fun delete() {
        glDeleteVertexArrays(pointer)
        vertexBuffer.delete()
        indexBuffer.delete()
    }

}
