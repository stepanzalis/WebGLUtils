package base.data

data class WebGlBuffer(
    val vertices: Array<Float>,
    val attributes: Array<WebGlAttribute>,
    val indices: Array<Short>,
    val colors: Array<Float>? = null,
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as WebGlBuffer

        if (!vertices.contentEquals(other.vertices)) return false
        if (!attributes.contentEquals(other.attributes)) return false
        if (!indices.contentEquals(other.indices)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vertices.contentHashCode()
        result = 31 * result + attributes.contentHashCode()
        result = 31 * result + indices.contentHashCode()
        return result
    }
}
