package transforms

import ext.toFloat32Array
import org.khronos.webgl.Float32Array

/**
 * A 4x4 matrix with common operations, immutable
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
open class Mat4 {

    val mat = Array(4) { DoubleArray(4) }
    /**
     * Creates a 4x4 matrix of value
     *
     * @param value
     * value of all elements of matrix
     */
    /**
     * Creates a zero 4x4 matrix
     */
    constructor(value: Double = 0.0) {
        for (i in 0..3) for (j in 0..3) mat[i][j] = value
    }

    /**
     * Creates a 4x4 matrix from row vectors
     *
     * @param p1
     * row 0 vector (M00, M01, M02, M03)
     * @param p2
     * row 1 vector (M10, M11, M12, M13)
     * @param p3
     * row 2 vector (M20, M21, M22, M23)
     * @param p4
     * row 3 vector (M30, M31, M32, M33)
     */
    constructor(p1: Point3D, p2: Point3D, p3: Point3D, p4: Point3D) {
        mat[0][0] = p1.x
        mat[0][1] = p1.y
        mat[0][2] = p1.z
        mat[0][3] = p1.w
        mat[1][0] = p2.x
        mat[1][1] = p2.y
        mat[1][2] = p2.z
        mat[1][3] = p2.w
        mat[2][0] = p3.x
        mat[2][1] = p3.y
        mat[2][2] = p3.z
        mat[2][3] = p3.w
        mat[3][0] = p4.x
        mat[3][1] = p4.y
        mat[3][2] = p4.z
        mat[3][3] = p4.w
    }

    /**
     * Creates a 4x4 matrix as a clone of the given 4x4 matrix
     *
     * @param m
     * 4x4 matrix to be cloned
     */
    constructor(m: Mat4) {
        for (i in 0..3) for (j in 0..3) mat[i][j] = m.mat[i][j]
    }

    /**
     * Creates a 4x4 matrix row-wise from a 16-element array of doubles
     *
     * @param m
     * double array of length 16 (asserted)
     */
    constructor(m: DoubleArray) {
        for (i in 0..3) for (j in 0..3) mat[i][j] = m[i * 4 + j]
    }

    /**
     * Creates a 4x4 matrix from a 4x4 array of doubles
     *
     * @param m
     * 2D double array of length 4x4 (asserted)
     */
    constructor(m: Array<DoubleArray>) {
        for (i in 0..3) {
            for (j in 0..3) mat[i][j] = m[i][j]
        }
    }

    /**
     * Returns the result of element-wise summation with the given 4x4 matrix
     *
     * @param m
     * 4x4 matrix
     * @return new Mat4 instance
     */
    fun add(m: Mat4): Mat4 {
        val result = Mat4()
        for (i in 0..3) for (j in 0..3) result.mat[i][j] = mat[i][j] + m.mat[i][j]
        return result
    }

    /**
     * Returns the result of element-wise multiplication by the given scalar value
     *
     * @param d
     * scalar value of type double
     * @return new Mat4 instance
     */
    fun mul(d: Double): Mat4 {
        val result = Mat4()
        for (i in 0..3) for (j in 0..3) result.mat[i][j] = mat[i][j] * d
        return result
    }

    /**
     * Returns the result of matrix multiplication by the given 4x4 matrix
     *
     * @param m
     * 4x4 matrix
     * @return new Mat4 instance
     */
    fun mul(m: Mat4): Mat4 {
        val result = Mat4()
        var sum: Double
        for (i in 0..3) for (j in 0..3) {
            sum = 0.0
            for (k in 0..3) sum += mat[i][k] * m.mat[k][j]
            result.mat[i][j] = sum
        }
        return result
    }

    /**
     * Returns a matrix element
     *
     * @param row
     * 0-based row index of the element
     * @param column
     * 0-based column index of the element
     * @return element value
     */
    operator fun get(row: Int, column: Int): Double {
        return mat[row][column]
    }

    /**
     * Returns a row vector at the given index
     *
     * @param row
     * 0-based row index
     * @return matrix row as a new Point3D instance
     */
    fun getRow(row: Int): Point3D {
        return Point3D(mat[row][0], mat[row][1], mat[row][2], mat[row][3])
    }

    /**
     * Returns a column vector at the given index
     *
     * @param column
     * 0-based column index
     * @return matrix column as a new Point3D instance
     */
    fun getColumn(column: Int): Point3D {
        return Point3D(mat[0][column], mat[1][column], mat[2][column], mat[3][column])
    }

    /**
     * Returns the transposition of this matrix
     *
     * @return new Mat3 instance
     */
    fun transpose(): Mat4 {
        val result = Mat4()
        for (i in 0..3) for (j in 0..3) {
            result.mat[i][j] = mat[j][i]
        }
        return result
    }

    /**
     * Returns the determinant of this matrix
     *
     * @return determinant value of type double
     */
    fun det(): Double {
        val s0 = mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1]
        val s1 = mat[0][0] * mat[1][2] - mat[1][0] * mat[0][2]
        val s2 = mat[0][0] * mat[1][3] - mat[1][0] * mat[0][3]
        val s3 = mat[0][1] * mat[1][2] - mat[1][1] * mat[0][2]
        val s4 = mat[0][1] * mat[1][3] - mat[1][1] * mat[0][3]
        val s5 = mat[0][2] * mat[1][3] - mat[1][2] * mat[0][3]
        val c5 = mat[2][2] * mat[3][3] - mat[3][2] * mat[2][3]
        val c4 = mat[2][1] * mat[3][3] - mat[3][1] * mat[2][3]
        val c3 = mat[2][1] * mat[3][2] - mat[3][1] * mat[2][2]
        val c2 = mat[2][0] * mat[3][3] - mat[3][0] * mat[2][3]
        val c1 = mat[2][0] * mat[3][2] - mat[3][0] * mat[2][2]
        val c0 = mat[2][0] * mat[3][1] - mat[3][0] * mat[2][1]
        return s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0
    }

    /**
     * Returns the inverse of this matrix if it exists or an empty Optional
     *
     * @return new Optional<Mat4> instance
    </Mat4> */
    fun inverse(): Mat4? {
        val s0 = mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1]
        val s1 = mat[0][0] * mat[1][2] - mat[1][0] * mat[0][2]
        val s2 = mat[0][0] * mat[1][3] - mat[1][0] * mat[0][3]
        val s3 = mat[0][1] * mat[1][2] - mat[1][1] * mat[0][2]
        val s4 = mat[0][1] * mat[1][3] - mat[1][1] * mat[0][3]
        val s5 = mat[0][2] * mat[1][3] - mat[1][2] * mat[0][3]
        val c5 = mat[2][2] * mat[3][3] - mat[3][2] * mat[2][3]
        val c4 = mat[2][1] * mat[3][3] - mat[3][1] * mat[2][3]
        val c3 = mat[2][1] * mat[3][2] - mat[3][1] * mat[2][2]
        val c2 = mat[2][0] * mat[3][3] - mat[3][0] * mat[2][3]
        val c1 = mat[2][0] * mat[3][2] - mat[3][0] * mat[2][2]
        val c0 = mat[2][0] * mat[3][1] - mat[3][0] * mat[2][1]
        val det = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0
        if (det == 0.0) return null
        val iDet = 1 / det
        val res = Mat4()
        res.mat[0][0] = (mat[1][1] * c5 - mat[1][2] * c4 + mat[1][3] * c3) * iDet
        res.mat[0][1] = (-mat[0][1] * c5 + mat[0][2] * c4 - mat[0][3] * c3) * iDet
        res.mat[0][2] = (mat[3][1] * s5 - mat[3][2] * s4 + mat[3][3] * s3) * iDet
        res.mat[0][3] = (-mat[2][1] * s5 + mat[2][2] * s4 - mat[2][3] * s3) * iDet
        res.mat[1][0] = (-mat[1][0] * c5 + mat[1][2] * c2 - mat[1][3] * c1) * iDet
        res.mat[1][1] = (mat[0][0] * c5 - mat[0][2] * c2 + mat[0][3] * c1) * iDet
        res.mat[1][2] = (-mat[3][0] * s5 + mat[3][2] * s2 - mat[3][3] * s1) * iDet
        res.mat[1][3] = (mat[2][0] * s5 - mat[2][2] * s2 + mat[2][3] * s1) * iDet
        res.mat[2][0] = (mat[1][0] * c4 - mat[1][1] * c2 + mat[1][3] * c0) * iDet
        res.mat[2][1] = (-mat[0][0] * c4 + mat[0][1] * c2 - mat[0][3] * c0) * iDet
        res.mat[2][2] = (mat[3][0] * s4 - mat[3][1] * s2 + mat[3][3] * s0) * iDet
        res.mat[2][3] = (-mat[2][0] * s4 + mat[2][1] * s2 - mat[2][3] * s0) * iDet
        res.mat[3][0] = (-mat[1][0] * c3 + mat[1][1] * c1 - mat[1][2] * c0) * iDet
        res.mat[3][1] = (mat[0][0] * c3 - mat[0][1] * c1 + mat[0][2] * c0) * iDet
        res.mat[3][2] = (-mat[3][0] * s3 + mat[3][1] * s1 - mat[3][2] * s0) * iDet
        res.mat[3][3] = (mat[2][0] * s3 - mat[2][1] * s1 + mat[2][2] * s0) * iDet
        return res
    }

    /**
     * Returns this matrix stored row-wise in a float array
     *
     * @return new float array
     */
    fun floatArray(): Float32Array {
        val result = FloatArray(16)
        for (i in 0..3) for (j in 0..3) result[i * 4 + j] = mat[i][j].toFloat()
        return Float32Array(result.toTypedArray())
    }
}