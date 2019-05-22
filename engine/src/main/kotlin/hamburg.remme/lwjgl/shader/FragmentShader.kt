package hamburg.remme.lwjgl.shader

import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER

class FragmentShader(source: String) : Shader(GL_FRAGMENT_SHADER, source)
