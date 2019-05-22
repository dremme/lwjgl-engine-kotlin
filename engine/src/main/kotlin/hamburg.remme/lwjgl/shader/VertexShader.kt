package hamburg.remme.lwjgl.shader

import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER

class VertexShader(source: String) : Shader(GL_VERTEX_SHADER, source)
