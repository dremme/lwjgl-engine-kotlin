package hamburg.remme.lwjgl

import hamburg.remme.lwjgl.input.Button
import hamburg.remme.lwjgl.input.ButtonListener
import hamburg.remme.lwjgl.input.Key
import hamburg.remme.lwjgl.input.KeyListener
import hamburg.remme.lwjgl.util.lateVal
import org.joml.Vector2f
import org.joml.Vector2fc
import org.lwjgl.BufferUtils.createDoubleBuffer
import org.lwjgl.BufferUtils.createIntBuffer
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback

class Window internal constructor() {

    var width: Int = 0
        private set
    var height: Int = 0
        private set
    val isOpen: Boolean get() = !glfwWindowShouldClose(pointer)
    var title: String
        get() = throw RuntimeException("Cannot get GLFW window title.")
        set(value) = glfwSetWindowTitle(pointer, value)
    var vSync: Boolean
        get() = throw RuntimeException("Cannot get GLFW swap interval.")
        set(value) = glfwSwapInterval(if (value) 1 else 0)

    private var pointer by lateVal<Long>()
    private val pWidth = createIntBuffer(1)
    private val pHeight = createIntBuffer(1)
    private val pMouseX = createDoubleBuffer(1)
    private val pMouseY = createDoubleBuffer(1)

    private val keys = mutableSetOf<Int>()
    private val buttons = mutableSetOf<Int>()
    private val mousePosition = Vector2f()

    init {
        GLFWErrorCallback.createPrint(System.err).set()
        glfwInit()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1) // highest we can go on MacOS
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE) // MacOS requires this
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE) // TODO: change later
    }

    fun getKey(key: Key): Boolean {
        return keys.contains(key.handle)
    }

    fun getButton(button: Button): Boolean {
        return buttons.contains(button.handle)
    }

    fun getMousePosition(): Vector2fc {
        return mousePosition
    }

    internal fun open(width: Int, height: Int, title: String) {
        pointer = glfwCreateWindow(width, height, title, 0, 0)
        glfwMakeContextCurrent(pointer)
        glfwShowWindow(pointer)
        initWindowSize()
        initListeners()
    }

    internal fun update() {
        glfwSwapBuffers(pointer)
        glfwPollEvents()

        // Mouse position
        // TODO: use the callback?
        glfwGetCursorPos(pointer, pMouseX, pMouseY)
        mousePosition.x = pMouseX[0].toFloat()
        mousePosition.y = pMouseY[0].toFloat()
    }

    private fun initWindowSize() {
        // TODO: call on FBO size changes
        glfwGetWindowSize(pointer, pWidth, pHeight)
        this.width = pWidth[0]
        this.height = pHeight[0]
    }

    private fun initListeners() {
        glfwSetMouseButtonCallback(pointer, ButtonListener(this::handleButtonEvent))
        glfwSetKeyCallback(pointer, KeyListener(this::handleKeyEvent))
    }

    private fun handleButtonEvent(button: Int, action: Int) {
        if (action == GLFW_PRESS) buttons.add(button)
        else if (action == GLFW_RELEASE) buttons.remove(button)
    }

    private fun handleKeyEvent(key: Int, action: Int) {
        if (action == GLFW_PRESS) keys.add(key)
        else if (action == GLFW_RELEASE) keys.remove(key)
    }

}
