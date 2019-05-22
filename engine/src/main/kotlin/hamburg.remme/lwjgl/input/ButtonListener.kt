package hamburg.remme.lwjgl.input

import org.lwjgl.glfw.GLFWMouseButtonCallback

class ButtonListener(private val callback: (Int, Int) -> Unit) : GLFWMouseButtonCallback() {

    override fun invoke(window: Long, button: Int, action: Int, mods: Int) {
        callback.invoke(button, action)
    }

}
