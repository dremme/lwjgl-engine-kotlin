package hamburg.remme.lwjgl.engine

import java.lang.System.currentTimeMillis

abstract class Application<T : Application<T>> {

    lateinit var window: Window
        private set
    lateinit var renderer: Renderer
        private set
    var time: Long = currentTimeMillis()
        private set
    var lastTime: Long = currentTimeMillis()
        private set

    private val gameObjects = mutableSetOf<GameObject<T>>()
    private lateinit var behaviors: Sequence<Behavior<T>>
    private var lastFps = currentTimeMillis()
    private var fps = 0

    fun launch(width: Int, height: Int, title: String) {
        window = Window()
        window.open(width, height, title)

        renderer = Renderer()

        start()

        while (window.isOpen) {
            // Timing and FPS stuff
            time = currentTimeMillis()
            lastTime = time
            if (time - lastFps > 1000) {
                window.title = "$title ($fps FPS)"
                fps = 0
                lastFps = time
            }
            fps++

            update()
            lateUpdate()

            renderer.update()
            window.update()
        }

        stop()
    }

    fun add(gameObject: GameObject<T>) {
        gameObject.behaviors.forEach { b ->
            b.gameObject = gameObject
            b.application = getThis()
            b.window = window
            b.renderer = renderer
        }
        gameObjects.add(gameObject)
        if (gameObject.hasModel) renderer.add(gameObject)
        gameObject.behaviors.forEach(Behavior<T>::start)
        updateBehaviors()
    }

    fun remove(gameObject: GameObject<T>) {
        gameObjects.remove(gameObject)
        if (gameObject.hasModel) renderer.remove(gameObject)
        gameObject.behaviors.forEach(Behavior<T>::stop)
        updateBehaviors()
    }

    private fun updateBehaviors() {
        behaviors = gameObjects.asSequence().flatMap { it.behaviors.asSequence() }
    }

    protected abstract fun getThis(): T

    protected abstract fun start()

    protected abstract fun stop()

    private fun update() {
        behaviors.filter(Behavior<T>::isEnabled).forEach(Behavior<T>::update)
    }

    private fun lateUpdate() {
        behaviors.filter(Behavior<T>::isEnabled).forEach(Behavior<T>::lateUpdate)
    }

}
