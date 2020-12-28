package transforms

/**
 * A 3x3 matrix with common operations, immutable
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
open class Mat3 {

    val mat = Array(3) { DoubleArray(3) }
    /**
     * Creates a 3x3 matrix of value
     *
     * @param value
     * value of all elements of matrix
     */
    /**
     * Creates a zero 3x3 matrix
     */
    constructor(value: Double = 0.0) {
        for (i in 0..2) for (j in 0..2) mat[i][j] = value
    }

    /**
     * Creates a 3x3 matrix from row vectors
     *
     * @param v1
     * row 0 vector (M00, M01, M02)
     * @param v2
     * row 1 vector (M10, M11, M12)
     * @param v3
     * row 2 vector (M20, M21, M22)
     */
    constructor(v1: Vec3D, v2: Vec3D, v3: Vec3D) {
        mat[0][0] = v1.x
        mat[0][1] = v1.y
        mat[0][2] = v1.z
        mat[1][0] = v2.x
        mat[1][1] = v2.y
        mat[1][2] = v2.z
        mat[2][0] = v3.x
        mat[2][1] = v3.y
        mat[2][2] = v3.z
    }

    /**
     * Creates a 3x3 matrix as a clone of the given 3x3 matrix
     *
     * @param m
     * 3x3 matrix to be cloned
     */
    constructor(m: Mat3) {
        for (i in 0..2) for (j in 0..2) mat[i][j] = m.mat[i][j]
    }

    /**
     * Creates a 3x3 matrix row-wise from a 9-element array of doubles
     *
     * @param m
     * double array of length 9 (asserted)
     */
    constructor(m: DoubleArray) {
        for (i in 0..2) for (j in 0..2) mat[i][j] = m[i * 3 + j]
    }

    /**
     * Creates a 3x3 matrix from a 3x3 array of doubles
     *
     * @param m
     * 2D double array of length 3x3 (asserted)
     */
    constructor(m: Array<DoubleArray>) {
        for (i in 0..2) {
            for (j in 0..2) mat[i][j] = m[i][j]
        }
    }

    /**
     * Creates a 3x3 matrix from a submatrix of a 4x4 matrix
     *
     * @param m
     * 4x4 matrix
     */
    constructor(m: Mat4) {
        for (i in 0..2) for (j in 0..2) mat[i][j] = m.mat[i][j]
    }

    /**
     * Returns the result of element-wise summation with the given 3x3 matrix
     *
     * @param m
     * 3x3 matrix
     * @return new Mat3 instance
     */
    fun add(m: Mat3): Mat3 {
        val result = Mat3()
        for (i in 0..2) for (j in 0..2) result.mat[i][j] = mat[i][j] + m.mat[i][j]
        return result
    }

    /**
     * Returns the result of element-wise multiplication by the given scalar value
     *
     * @param d
     * scalar value of type double
     * @return new Mat3 instance
     */
    fun mul(d: Double): Mat3 {
        val result = Mat3()
        for (i in 0..2) for (j in 0..2) result.mat[i][j] = mat[i][j] * d
        return result
    }

    /**
     * Returns the result of matrix multiplication by the given 3x3 matrix
     *
     * @param m
     * 3x3 matrix
     * @return new Mat3 instance
     */
    fun mul(m: Mat3): Mat3 {
        val result = Mat3()
        var sum: Double
        for (i in 0..2) for (j in 0..2) {
            sum = 0.0
            for (k in 0..2) sum += mat[i][k] * m.mat[k][j]
            result.mat[i][j] = sum
        }
        return result
    }

    /**
     * Returns a clone of this matrix with the given element replaced by the
     * given value
     *
     * @param row
     * 0-based row index of the element to change
     * @param column
     * 0-based column index of the element to change
     * @param element
     * new element value
     * @return new Mat3 instance
     */
    fun withElement(row: Int, column: Int, element: Double): Mat3 {
        val result = Mat3(this)
        result.mat[row][column] = element
        return result
    }

    /**
     * Returns a clone of this matrix with the given row replaced by the given
     * vector
     *
     * @param index
     * 0-based row index
     * @param row
     * new row vector
     * @return new Mat3 instance
     */
    fun withRow(index: Int, row: Vec3D): Mat3 {
        val result = Mat3(this)
        result.mat[index][0] = row.x
        result.mat[index][1] = row.y
        result.mat[index][2] = row.z
        return result
    }

    /**
     * Returns a clone of this matrix with the given column replaced by the
     * given vector
     *
     * @param index
     * 0-based column index
     * @param column
     * new column vector
     * @return new Mat3 instance
     */
    fun withColumn(index: Int, column: Vec3D): Mat3 {
        val result = Mat3(this)
        result.mat[0][index] = column.x
        result.mat[1][index] = column.y
        result.mat[2][index] = column.z
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
     * @return matrix row as a new Vec3D instance
     */
    fun getRow(row: Int): Vec3D {
        return Vec3D(mat[row][0], mat[row][1], mat[row][2])
    }

    /**
     * Returns a column vector at the given index
     *
     * @param column
     * 0-based column index
     * @return matrix column as a new Vec3D instance
     */
    fun getColumn(column: Int): Vec3D {
        return Vec3D(mat[0][column], mat[1][column], mat[2][column])
    }

    /**
     * Returns the transposition of this matrix
     *
     * @return new Mat3 instance
     */
    fun transpose(): Mat3 {
        val result = Mat3()
        for (i in 0..2) for (j in 0..2) result.mat[i][j] = mat[j][i]
        return result
    }

    /**
     * Returns the determinant of this matrix
     *
     * @return determinant value of type double
     */
    fun det(): Double {
        return (mat[0][0] * (mat[1][1] * mat[2][2] - mat[2][1] * mat[1][2])
                - mat[0][1] * (mat[1][0] * mat[2][2] - mat[2][0] * mat[1][2])
                + mat[0][2] * (mat[1][0] * mat[2][1] - mat[2][0] * mat[1][1]))
    }

    /**
     * Returns the inverse of this matrix if it exists or an empty Optional
     *
     * @return new Optional<Mat3> instance
    </Mat3> */
    fun inverse(): Mat3? {
        val det = det()
        if (det == 0.0) return null
        val res = Mat3()
        res.mat[0][0] = (mat[1][1] * mat[2][2] - mat[1][2] * mat[2][1]) / det
        res.mat[0][1] = (mat[0][2] * mat[2][1] - mat[0][1] * mat[2][2]) / det
        res.mat[0][2] = (mat[0][1] * mat[1][2] - mat[0][2] * mat[1][1]) / det
        res.mat[1][0] = (mat[1][2] * mat[2][0] - mat[1][0] * mat[2][2]) / det
        res.mat[1][1] = (mat[0][0] * mat[2][2] - mat[0][2] * mat[2][0]) / det
        res.mat[1][2] = (mat[0][2] * mat[1][0] - mat[0][0] * mat[1][2]) / det
        res.mat[2][0] = (mat[1][0] * mat[2][1] - mat[1][1] * mat[2][0]) / det
        res.mat[2][1] = (mat[0][1] * mat[2][0] - mat[0][0] * mat[2][1]) / det
        res.mat[2][2] = (mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0]) / det
        return res
    }

    /**
     * Returns this matrix stored row-wise in a float array
     *
     * @return new float array
     */
    fun floatArray(): FloatArray {
        val result = FloatArray(9)
        for (i in 0..2) for (j in 0..2) result[i * 3 + j] = mat[i][j].toFloat()
        return result
    }
}