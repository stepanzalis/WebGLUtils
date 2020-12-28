package transforms

/**
 * 3D vector over real numbers (final double-precision), equivalent to 3D affine
 * point, immutable
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Vec3D {
    /**
     * Returns the x coordinate
     *
     * @return the x
     */
    val x: Double

    /**
     * Returns the y coordinate
     *
     * @return the y
     */
    val y: Double

    /**
     * Returns the z coordinate
     *
     * @return the z
     */
    val z: Double

    /**
     * Creates a zero vector
     */
    constructor() {
        z = 0.0
        y = z
        x = y
    }

    /**
     * Creates a vector with the given coordinates
     *
     * @param x
     * x coordinate
     * @param y
     * y coordinate
     * @param z
     * z coordinate
     */
    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    /**
     * Creates a vector with all coordinates set to the given value
     *
     * @param value
     * coordinate
     */
    constructor(value: Double) {
        x = value
        y = value
        z = value
    }

    /**
     * Creates a vector by cloning the given one
     *
     * @param v
     * vector to be cloned
     */
    constructor(v: Vec3D) {
        x = v.x
        y = v.y
        z = v.z
    }

    /**
     * Creates a vector by ignoring the fourth homogeneous coordinate w of the
     * given homogeneous 3D point
     *
     * @param point
     * homogeneous 3D point whose x,y,z will be cloned
     */
    constructor(point: Point3D) {
        x = point.x
        y = point.y
        z = point.z
    }

    /**
     * Creates a vector by extracting coordinates from the given array of
     * doubles
     *
     * @param array
     * double array of size 3 (asserted)
     */
    constructor(array: DoubleArray) {
        x = array[0]
        y = array[1]
        z = array[2]
    }

    /**
     * Returns a clone of this vector with the x coordinate replaced by the
     * given value
     *
     * @param x
     * x coordinate
     * @return new Vec3D instance
     */
    fun withX(x: Double): Vec3D {
        return Vec3D(x, y, z)
    }

    /**
     * Returns a clone of this vector with the y coordinate replaced by the
     * given value
     *
     * @param y
     * y coordinate
     * @return new Vec3D instance
     */
    fun withY(y: Double): Vec3D {
        return Vec3D(x, y, z)
    }

    /**
     * Returns a clone of this vector with the z coordinate replaced by the
     * given value
     *
     * @param z
     * z coordinate
     * @return new Vec3D instance
     */
    fun withZ(z: Double): Vec3D {
        return Vec3D(x, y, z)
    }

    /**
     * Returns 2D vector with this x and y coordinates, i.e. an orthogonal
     * projection of this as an affine point into the xy plane
     *
     * @return new Vec2D instance
     */
    fun ignoreZ(): transforms.Vec2D {
        return transforms.Vec2D(x, y)
    }

    /**
     * Returns the result of vector addition of the given vector
     *
     * @param v
     * vector to add
     * @return new Vec3D instance
     */
    fun add(v: Vec3D): Vec3D {
        return Vec3D(x + v.x, y + v.y, z + v.z)
    }

    /**
     * Returns the result of vector subtraction of the given vector
     *
     * @param v
     * vector to subtract
     * @return new Vec3D instance
     */
    fun sub(v: Vec3D): Vec3D {
        return Vec3D(x - v.x, y - v.y, z - v.z)
    }

    /**
     * Returns the result of scalar multiplication
     *
     * @param d
     * scalar value of type double
     * @return new Vec3D instance
     */
    fun mul(d: Double): Vec3D {
        return Vec3D(x * d, y * d, z * d)
    }

    /**
     * Returns the result of multiplication by the given 3x3 matrix thus
     * applying the transformation contained within
     *
     * @param m
     * 3x3 matrix
     * @return new Vec3D instance
     */
    fun mul(m: Mat3): Vec3D {
        return Vec3D(
            m.mat.get(0).get(0) * x + m.mat.get(1).get(0) * y + m.mat.get(2).get(0) * z,
            m.mat.get(0).get(1) * x + m.mat.get(1).get(1) * y + m.mat.get(2).get(1) * z,
            m.mat.get(0).get(2) * x + m.mat.get(1).get(2) * y + m.mat.get(2).get(2) * z
        )
    }

    /**
     * Returns the result of element-wise multiplication with the given vector
     *
     * @param v
     * 3D vector
     * @return new Vec3D instance
     */
    fun mul(v: Vec3D): Vec3D {
        return Vec3D(x * v.x, y * v.y, z * v.z)
    }

    /**
     * Returns the result of dot-product with the given vector
     *
     * @param v
     * 3D vector
     * @return double-precision floating point value
     */
    fun dot(v: Vec3D): Double {
        return x * v.x + y * v.y + z * v.z
    }

    /**
     * Returns the result of cross-product with the given vector, i.e. a vector
     * perpendicular to both this and the given vector, the direction is
     * right-handed
     *
     * @param v
     * 3D vector
     * @return new Vec3D instance
     */
    fun cross(v: Vec3D): Vec3D {
        return Vec3D(
            y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y
                    * v.x
        )
    }

    /**
     * Returns a collinear unit vector (by dividing all vector components by
     * vector length) if possible (nonzero length), empty Optional otherwise
     *
     * @return new Optional<Vec3D> instance
    </Vec3D> */
    fun normalized(): Vec3D? {
        val len = length()
        return if (len == 0.0) null else Vec3D(x / len, y / len, z / len)
    }

    /**
     * Returns the vector opposite to this vector
     *
     * @return new Vec2D instance
     */
    fun opposite(): Vec3D {
        return Vec3D(-x, -y, -z)
    }

    /**
     * Returns the length of this vector
     *
     * @return double-precision floating point value
     */
    fun length(): Double {
        return kotlin.math.sqrt(x * x + y * y + z * z)
    }

}