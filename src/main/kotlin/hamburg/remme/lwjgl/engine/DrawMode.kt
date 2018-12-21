package hamburg.remme.lwjgl.engine

import org.lwjgl.opengl.GL11.*

enum class DrawMode(internal val handle: Int) {

    POINTS(GL_POINTS),
    LINES(GL_LINES),
    TRIANGLES(GL_TRIANGLES)

}
