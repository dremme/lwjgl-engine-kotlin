package hamburg.remme.lwjgl.engine.input

import org.lwjgl.glfw.GLFW.*

enum class Button(internal val handle: Int) {

    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE)

}
