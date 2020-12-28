package transforms

/**
 * 3D point with homogeneous coordinates, immutable
 *
 * @author PGRF FIM UHK
 * @version 2014
 */
class Point3D {
    /**
     * Returns the homogeneous x coordinate
     *
     * @return the x
     */
    val x: Double

    /**
     * Returns the homogeneous y coordinate
     *
     * @return the y
     */
    val y: Double

    /**
     * Returns the homogeneous z coordinate
     *
     * @return the z
     */
    val z: Double

    /**
     * Returns the homogeneous w coordinate
     *
     * @return the w
     */
    val w: Double

    /**
     * Creates a homogeneous point representing the origin
     */
    constructor() {
        z = 0.0
        y = z
        x = y
        w = 1.0
    }

    /**
     * Creates a homogeneous point representing a 3D point with the three given
     * affine coordinates
     *
     * @param x
     * affine x coordinate
     * @param y
     * affine y coordinate
     * @param z
     * affine z coordinate
     */
    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
        w = 1.0
    }

    /**
     * Creates a point with the given homogeneous coordinates
     *
     * @param x
     * homogeneous x coordinate
     * @param y
     * homogeneous y coordinate
     * @param z
     * homogeneous z coordinate
     * @param w
     * homogeneous w coordinate
     */
    constructor(x: Double, y: Double, z: Double, w: Double) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    /**
     * Creates a homogeneous point representing a 3D affine point defined by the
     * given vector from origin
     *
     * @param v
     * affine coordinates vector (vector from origin to the point)
     */
    constructor(v: Vec3D) {
        x = v.x
        y = v.y
        z = v.z
        w = 1.0
    }

    /**
     * Creates a point by cloning the give one
     *
     * @param p
     * homogeneous point to be cloned
     */
    constructor(p: Point3D) {
        x = p.x
        y = p.y
        z = p.z
        w = p.w
    }

    /**
     * Creates a point by extracting homogeneous coordinates from the given
     * array of doubles
     *
     * @param array
     * double array of size 4 (asserted)
     */
    constructor(array: DoubleArray) {
        x = array[0]
        y = array[1]
        z = array[2]
        w = array[3]
    }

    /**
     * Returns a clone of this point with the homogeneous x coordinate replaced by the
     * given value
     *
     * @param x
     * homogeneous x coordinate
     * @return new Point3D instance
     */
    fun withX(x: Double): Point3D {
        return Point3D(x, y, z, w)
    }

    /**
     * Returns a clone of this point with the homogeneous y coordinate replaced by the
     * given value
     *
     * @param y
     * homogeneous y coordinate
     * @return new Point3D instance
     */
    fun withY(y: Double): Point3D {
        return Point3D(x, y, z, w)
    }

    /**
     * Returns a clone of this point with the homogeneous z coordinate replaced by the
     * given value
     *
     * @param z
     * homogeneous z coordinate
     * @return new Point3D instance
     */
    fun withZ(z: Double): Point3D {
        return Point3D(x, y, z, w)
    }

    /**
     * Returns a clone of this point with the homogeneous w coordinate replaced by the
     * given value
     *
     * @param w
     * homogeneous w coordinate
     * @return new Point3D instance
     */
    fun withW(w: Double): Point3D {
        return Point3D(x, y, z, w)
    }

    /**
     * Returns the result of element-wise summation with the given homogeneous
     * 3D point
     *
     * @param p
     * homogeneous 3D point to sum
     * @return new Point3D instance
     */
    fun add(p: Point3D): Point3D {
        return Point3D(x + p.x, y + p.y, z + p.z, w + p.w)
    }

    /**
     * Returns the result of element-wise multiplication by the given scalar value
     *
     * @param d
     * scalar value of type double
     * @return new Point3D instance
     */
    fun mul(d: Double): Point3D {
        return Point3D(x * d, y * d, z * d, w * d)
    }

    /**
     * Returns the result of point dehomogenization, i.e. the affine point
     * coordinates = x,y,z divided by w, in the form of a vector from origin to
     * the affine point if possible (the point is not in infinity), empty
     * Optional otherwise
     *
     * @return new Optional<Vec3D> instance
    </Vec3D> */
    fun dehomog(): Vec3D? {
        return if (w == 0.0) null else Vec3D(x / w, y / w, z / w)
    }

    /**
     * Converts the homogeneous 3D point to 3D vector by ignoring the fourth
     * homogeneous coordinate w
     *
     * @return new Vec3D instance
     */
    fun ignoreW(): Vec3D {
        return Vec3D(x, y, z)
    }
}