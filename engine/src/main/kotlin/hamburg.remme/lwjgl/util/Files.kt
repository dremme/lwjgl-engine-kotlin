package hamburg.remme.lwjgl.util

import hamburg.remme.lwjgl.Application
import java.io.InputStream
import java.net.URI

internal fun readResource(res: String): String {
    return streamResource(res).bufferedReader().readText()
}

internal fun streamResource(res: String): InputStream {
    return Application::class.java.getResourceAsStream(res)
}

internal fun resourceURI(res: String): URI {
    return Application::class.java.getResource(res).toURI()
}
