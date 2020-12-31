package base.data

data class WebGlAttribute(
    val name: String,
    val dimension: Int,
    val stride: Int = 0,
    val offset: Int = 0,
)