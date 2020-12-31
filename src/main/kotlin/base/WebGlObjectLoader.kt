package base

import transforms.Point3D
import transforms.Vec3D

/**
 * Helper class to load [.obj] files
 * @param source source of object file
 */
class WebGlObjectLoader(private val source: String? = null) {

    private val points = mutableListOf<Point3D>()
    private val faces = mutableListOf<Vec3D>()
    private val normals = mutableListOf<Vec3D>()

    init {
        source?.let { parseModelFromSource(source) }
    }

    /**
     * Parsing [.obj] model from source
     * When source is null or empty, parsing is skipped
     */
    fun parseModelFromSource(source: String?) {
        val lines = source?.split('\n') ?: emptyList()

        lines.forEach { line ->
            val values = line.split(' ').filter { it.isNotBlank() }
            when (values.firstOrNull()) {
                Vertex -> points.add(
                    Point3D(
                        values[1].toDouble(),
                        values[2].toDouble(),
                        values[3].toDouble()
                    )
                )
                Face -> faces.add(
                    Vec3D(
                        values[1].toDouble() - 1,
                        values[2].toDouble() - 1,
                        values[3].toDouble() - 1
                    )
                )
                VertexNormal -> normals.add(
                    Vec3D(
                        values[1].toDouble(),
                        values[2].toDouble(),
                        values[3].toDouble()
                    )
                )
            }
        }

        if (normals.isEmpty()) {
            computeNormals()
        }
    }

    private fun computeNormals() {
        val tmpNormals = Array(points.size) { Vec3D() }
        faces.forEach { face ->
            val idx1 = face.x.toInt()
            val idx2 = face.y.toInt()
            val idx3 = face.z.toInt()

            val p1 = points[face.x.toInt()]
            val p2 = points[face.y.toInt()]
            val p3 = points[face.z.toInt()]

            val w = Vec3D(
                (p2.x - p1.x),
                (p2.y - p1.y),
                (p2.z - p1.z)
            )
            val v = Vec3D(
                (p3.x - p1.x),
                (p3.y - p1.y),
                (p3.z - p1.z)
            )
            val normal = v.cross(w)
            normal.normalized()

            tmpNormals[idx1].add(normal)
            tmpNormals[idx2].add(normal)
            tmpNormals[idx3].add(normal)
        }

        tmpNormals.forEach {
            it.normalized()
            normals.add(it)
        }
    }

    fun getVerticesArray() = points.flatMap { listOf(it.x, it.y, it.z) }.map { it.toFloat() }.toTypedArray()
    fun getVertexNormalsArray() = normals.flatMap { listOf(it.x, it.y, it.z) }
    fun getFacesArray() = faces.flatMap { listOf(it.x, it.y, it.z) }.map { it.toInt().toShort() }.toTypedArray()

    fun getNumFaces(): Int = faces.size

    companion object {
        private const val Vertex = "v"
        private const val VertexNormal = "vn"
        private const val Face = "f"
    }
}