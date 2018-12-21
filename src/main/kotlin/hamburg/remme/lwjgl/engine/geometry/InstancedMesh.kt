package hamburg.remme.lwjgl.engine.geometry

import org.joml.Matrix4fc
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL31.glDrawElementsInstanced
import org.lwjgl.opengl.GL33.glVertexAttribDivisor

class InstancedMesh(data: FloatArray, indices: ShortArray, private val instances: Int) : Mesh(data, indices) {

    // Bind matrix buffer; pre-allocating instances * mat4
    private val modelViewBuffer = Buffer(GL_ARRAY_BUFFER, FloatArray(V4_SIZE * instances))
    // VAO remembers the bound buffer

    init {
        // Bind matrix attributes
        glEnableVertexAttribArray(3)
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 4 * V4_SIZE, 0L * V4_SIZE)
        glEnableVertexAttribArray(4)
        glVertexAttribPointer(4, 4, GL_FLOAT, false, 4 * V4_SIZE, 1L * V4_SIZE)
        glEnableVertexAttribArray(5)
        glVertexAttribPointer(5, 4, GL_FLOAT, false, 4 * V4_SIZE, 2L * V4_SIZE)
        glEnableVertexAttribArray(6)
        glVertexAttribPointer(6, 4, GL_FLOAT, false, 4 * V4_SIZE, 3L * V4_SIZE)

        glVertexAttribDivisor(3, 1)
        glVertexAttribDivisor(4, 1)
        glVertexAttribDivisor(5, 1)
        glVertexAttribDivisor(6, 1)
    }

    fun updateModelViewBuffer(matrices: List<Matrix4fc>) {
        if (matrices.size != instances) throw RuntimeException() // TODO: message

        val data = FloatArray(V4_SIZE * instances)
        matrices.indices.forEach { matrices[it].get(data, V4_SIZE * it) }
        modelViewBuffer.update(data)
    }

    override fun draw(drawMode: Int) {
        glDrawElementsInstanced(
            drawMode,
            size,
            GL_UNSIGNED_SHORT,
            0,
            instances
        )
    }

    override fun delete() {
        super.delete()
        modelViewBuffer.delete()
    }

}
