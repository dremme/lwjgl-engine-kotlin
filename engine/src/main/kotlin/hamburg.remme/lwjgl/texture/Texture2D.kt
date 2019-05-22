package hamburg.remme.lwjgl.texture

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.glGenerateMipmap

class Texture2D(image: Image) : Texture(GL_TEXTURE_2D) {

    init {
        val format = if (image.channels == 3) {
            // TODO: simplify?
            if (image.width and 3 != 0) glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (image.width and 1))
            GL_RGB
        } else {
            GL_RGBA
        }
        glTexImage2D(GL_TEXTURE_2D, 0, format, image.width, image.height, 0, format, GL_UNSIGNED_BYTE, image.data)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glGenerateMipmap(GL_TEXTURE_2D)
    }

}
