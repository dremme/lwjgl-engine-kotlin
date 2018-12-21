package hamburg.remme.lwjgl.engine.shader

import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER

class FragmentShader(source: String) : Shader(GL_FRAGMENT_SHADER, source)
