package base.data

abstract class GeometryDTO(
    val indices: Array<Short>,
    val vertices: Array<Float>,
    val colors: Array<Float> = emptyArray(),
    val normals: Array<Float> = emptyArray(),
)