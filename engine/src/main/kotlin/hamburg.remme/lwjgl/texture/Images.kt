package hamburg.remme.lwjgl.texture

import hamburg.remme.lwjgl.util.resourceURI
import org.lwjgl.BufferUtils.createByteBuffer
import org.lwjgl.stb.STBImage.stbi_info_from_memory
import org.lwjgl.stb.STBImage.stbi_load_from_memory
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.experimental.and

fun loadImage(imageFile: String): Image {
    val raw = readFile(imageFile)
    MemoryStack.stackPush().use {
        val pWidth = it.mallocInt(1)
        val pHeight = it.mallocInt(1)
        val pComp = it.mallocInt(1)

        stbi_info_from_memory(raw, pWidth, pHeight, pComp)
        val data = stbi_load_from_memory(raw, pWidth, pHeight, pComp, 0)!!

        // Pre-multiply alpha values for correctness if there is an alpha channel
        if (pComp.get() == 4) premultiplyAlpha(data, pWidth.get(), pHeight.get())

        return Image(pWidth.get(), pHeight.get(), pComp.get(), data)
    }
}

private fun readFile(file: String): ByteBuffer {
    val uri = resourceURI(file)
    val bytes = Files.readAllBytes(Paths.get(uri))
    val buffer = createByteBuffer(bytes.size)
    buffer.put(bytes)
    buffer.flip()
    return buffer
}

private const val FF = 0xFF.toByte()

private fun premultiplyAlpha(data: ByteBuffer, w: Int, h: Int) {
    val stride = w * 4
    for (y in 0 until h) {
        for (x in 0 until w) {
            val i = y * stride + x * 4
            val alpha = (data[i + 3] and FF) / 255.0f
            data.put(i + 0, Math.round((data[i + 0] and FF) * alpha).toByte())
            data.put(i + 1, Math.round((data[i + 1] and FF) * alpha).toByte())
            data.put(i + 2, Math.round((data[i + 2] and FF) * alpha).toByte())
        }
    }
}
