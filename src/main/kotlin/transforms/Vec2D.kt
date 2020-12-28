package transforms


/**
 * 2D vector over real numbers (final double-precision), equivalent to 2D affine
 * point, immutable
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Vec2D {
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
     * Creates a zero vector
     */
    constructor() {
        y = 0.0
        x = y
    }

    /**
     * Creates a vector with the given coordinates
     *
     * @param x
     * x coordinate
     * @param y
     * y coordinate
     */
    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
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
    }

    /**
     * Creates a vector by cloning the give one
     *
     * @param v
     * vector to be cloned
     */
    constructor(v: Vec2D) {
        x = v.x
        y = v.y
    }

    /**
     * Returns a clone of this vector with the x coordinate replaced by the
     * given value
     *
     * @param x
     * x coordinate
     * @return new Vec2D instance
     */
    fun withX(x: Double): Vec2D {
        return Vec2D(x, y)
    }

    /**
     * Returns a clone of this vector with the y coordinate replaced by the
     * given value
     *
     * @param y
     * y coordinate
     * @return new Vec2D instance
     */
    fun withY(y: Double): Vec2D {
        return Vec2D(x, y)
    }

    /**
     * Returns the result of vector addition of the given vector
     *
     * @param v
     * vector to add
     * @return new Vec2D instance
     */
    fun add(v: Vec2D): Vec2D {
        return Vec2D(x + v.x, y + v.y)
    }

    /**
     * Returns the result of vector subtraction of the given vector
     *
     * @param v
     * vector to subtract
     * @return new Vec2D instance
     */
    fun sub(v: Vec2D): Vec2D {
        return Vec2D(x - v.x, y - v.y)
    }

    /**
     * Returns the result of scalar multiplication
     *
     * @param d
     * scalar value of type double
     * @return new Vec2D instance
     */
    fun mul(d: Double): Vec2D {
        return Vec2D(x * d, y * d)
    }

    /**
     * Returns the result of element-wise multiplication with the given vector
     *
     * @param v
     * 2D vector
     * @return new Vec2D instance
     */
    fun mul(v: Vec2D): Vec2D {
        return Vec2D(x * v.x, y * v.y)
    }

    /**
     * Returns the result of dot-product with the given vector
     *
     * @param v
     * 2D vector
     * @return double-precision floating point value
     */
    fun dot(v: Vec2D): Double {
        return x * v.x + y * v.y
    }

    /**
     * Returns a collinear unit vector (by dividing all vector components by
     * vector length) if possible (nonzero length), empty Optional otherwise
     *
     * @return new Optional<Vec2D> instance
    </Vec2D> */
    fun normalized(): Vec2D? {
        val len = length()
        return if (len == 0.0) null else Vec2D(x / len, y / len)
    }

    /**
     * Returns the vector opposite to this vector
     *
     * @return new Vec2D instance
     */
    fun opposite(): Vec2D {
        return Vec2D(-x, -y)
    }

    /**
     * Returns the length of this vector
     *
     * @return double-precision floating point value
     */
    fun length(): Double {
        return kotlin.math.sqrt(x * x + y * y)
    }
}