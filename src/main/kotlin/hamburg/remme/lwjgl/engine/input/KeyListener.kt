package hamburg.remme.lwjgl.engine.input

import org.lwjgl.glfw.GLFWKeyCallback

class KeyListener(private val callback: (Int, Int) -> Unit) : GLFWKeyCallback() {

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        callback.invoke(key, action)
    }

}
