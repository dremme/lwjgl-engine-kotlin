package hamburg.remme.lwjgl.engine

import hamburg.remme.lwjgl.engine.geometry.Mesh
import hamburg.remme.lwjgl.engine.shader.ShaderProgram
import hamburg.remme.lwjgl.engine.texture.Texture
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.*

class Renderer internal constructor() {

    var camera: Matrix4f = Matrix4f()
    var clearColor: Vector3f = Vector3f(0f)
    var drawMode: DrawMode = DrawMode.TRIANGLES

    private val renderQueue = mutableSetOf<GameObject<*>>()
    private val mvp = Matrix4f()
    private var shader: ShaderProgram? = null
    private var texture: Texture? = null
    private var mesh: Mesh? = null

    init {
        createCapabilities()
    }

    fun enableTransparency() {
        glEnable(GL_BLEND)
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)
    }

    fun disableTransparency() {
        glDisable(GL_BLEND)
    }

    fun enableTextures() {
        glEnable(GL_TEXTURE_2D)
    }

    fun disableTextures() {
        glDisable(GL_TEXTURE_2D)
    }

    fun enableDepthTest() {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS) // FIXME: use proper func; default?
    }

    fun disableDepthTest() {
        glDisable(GL_DEPTH_TEST)
    }

    internal fun add(gameObject: GameObject<*>) {
        renderQueue.add(gameObject)
    }

    internal fun remove(gameObject: GameObject<*>) {
        renderQueue.remove(gameObject)
    }

    internal fun update() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(clearColor.x, clearColor.y, clearColor.z, 1f)
        renderQueue.forEach(this::render)
    }

    private fun render(gameObject: GameObject<*>) {
        val material = gameObject.model!!.material

        use(material.shader)
        use(material.texture)

        shader?.let {
            it["world_matrix"] = calculateMvpMatrix(gameObject)
            it["tint"] = material.tint
            it["has_albedo"] = material.hasTexture
            it["albedo_map"] = 0 // TODO: move to shader init?
        }

        useAndDraw(gameObject.model!!.mesh)
    }

    private fun calculateMvpMatrix(gameObject: GameObject<*>): Matrix4f {
        // TODO: can be optimized by creating a camera class
        return mvp.set(camera)
            // TODO: can be optimized by moving this to model and memorizing it
            .translate(gameObject.position)
            .rotate(gameObject.rotation)
            .scale(gameObject.scale)
    }

    private fun use(shader: ShaderProgram) {
        if (this.shader !== shader) {
            this.shader = shader
            shader.use()
        }
    }

    private fun use(texture: Texture?) {
        if (this.texture !== texture) {
            this.texture = texture
            if (texture == null) {
                Texture.free(0)
            } else {
                texture.bind(0) // TODO: select proper texture unit
            }
        }
    }

    private fun useAndDraw(mesh: Mesh) {
        if (this.mesh !== mesh) {
            this.mesh = mesh
            mesh.bind()
        }
        mesh.draw(drawMode.handle)
    }

}
