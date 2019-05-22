package hamburg.remme.lwjgl.geometry

class Quad : Mesh(data, indices) {

    private companion object {

        val data = floatArrayOf(
            -1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f
        )
        val indices = shortArrayOf(0, 1, 2, 2, 3, 0) // FIXME: optimize index order, "k-sorting"

    }

}
