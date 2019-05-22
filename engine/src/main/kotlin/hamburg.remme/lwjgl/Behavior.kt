package hamburg.remme.lwjgl

abstract class Behavior<T : Application<T>>(var isEnabled: Boolean = true) {

    lateinit var gameObject: GameObject<T>
        internal set
    lateinit var application: T
        internal set
    lateinit var window: Window
        internal set
    lateinit var renderer: Renderer
        internal set

    open fun start() {}

    open fun update() {}

    open fun lateUpdate() {}

    open fun stop() {}

}
