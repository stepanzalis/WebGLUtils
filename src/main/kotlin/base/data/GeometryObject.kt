package base.data

/**
 * Any geometry object can be created by inheritance of this class (cube, triangle, etc)
 * @param indices is required
 * @param vertices is required
 */
abstract class GeometryObject(
    val indices: Array<Short>,
    val vertices: Array<Float>,
    val colors: Array<Float> = emptyArray(),
    val normals: Array<Float> = emptyArray(),
)