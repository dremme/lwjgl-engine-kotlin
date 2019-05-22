package hamburg.remme.lwjgl.texture

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture

abstract class Texture(private val target: Int) {

    companion object {

        fun free(unit: Int) {
            glActiveTexture(GL_TEXTURE0 + unit)
            glBindTexture(GL_TEXTURE_2D, 0) // FIXME: is GL_TEXTURE_2D always valid?
        }

    }

    private val pointer = glGenTextures()

    init {
        glBindTexture(target, pointer)
    }

    fun bind(unit: Int) {
        glActiveTexture(GL_TEXTURE0 + unit)
        glBindTexture(target, pointer)
    }

    fun delete() {
        glDeleteTextures(pointer)
    }

}
