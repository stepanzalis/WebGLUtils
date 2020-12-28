package base

import base.data.Face
import base.data.Point
import ext.toFloat32Array
import ext.toUint16Array
import transforms.Vec3D

/**
 * Helper class to load [.obj] files
 * @param source source of object file
 */
class WebGlObjectLoader(private val source: String? = null) {

    private val points = mutableListOf<Point>()
    private val faces = mutableListOf<Face>()
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
            when (values.getOrNull(0)) {
                "v" -> points.add(
                    Point(
                        values[1].toDouble(),
                        values[2].toDouble(),
                        values[3].toDouble()
                    )
                )
                "f" -> faces.add(
                    Face(
                        values[1].toDouble() - 1,
                        values[2].toDouble() - 1,
                        values[3].toDouble() - 1
                    )
                )
                "vn" -> normals.add(
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
            val idx1 = face.p1.toInt()
            val idx2 = face.p2.toInt()
            val idx3 = face.p3.toInt()

            val p1 = points[idx1]
            val p2 = points[idx2]
            val p3 = points[idx3]

            val w = Vec3D(
                (p2.x - p1.x).toDouble(),
                (p2.y - p1.y).toDouble(),
                (p2.z - p1.z).toDouble()
            )
            val v = Vec3D(
                (p3.x - p1.x).toDouble(),
                (p3.y - p1.y).toDouble(),
                (p3.z - p1.z).toDouble()
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

    fun getVertices() = points.flatMap { it.list() }.toFloat32Array()
    fun getVerticesArray() = points.flatMap { it.list() }.toTypedArray()

    fun getVertexNormals() = normals.flatMap { listOf(it.x, it.y, it.z) }

    fun getVertexNormalsArray() = normals.flatMap { listOf(it.x, it.y, it.z) }

    fun getFaces() = faces.flatMap { it.list() }.toUint16Array()
    fun getFacesArray() = faces.flatMap { it.list() }.toTypedArray()

    fun getColors() = points.flatMap { it.color.list() }.toFloat32Array()

    fun getNumFaces(): Int = faces.size
}