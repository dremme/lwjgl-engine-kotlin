package hamburg.remme.lwjgl.shader

import hamburg.remme.lwjgl.util.readResource

fun createShader(vertexFile: String, fragmentFile: String): ShaderProgram {
    return ShaderProgram(
        VertexShader(readResource(vertexFile)),
        FragmentShader(readResource(fragmentFile))
    )
}
