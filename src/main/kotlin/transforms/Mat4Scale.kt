package transforms

/**
 * A 4x4 matrix of 3D scaling
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4Scale(x: Double, y: Double, z: Double) : Mat4Identity() {

    /**
     * Creates a 4x4 transformation matrix equivalent to uniform scaling in 3D
     *
     * @param scale
     * x,y,z -axis scale factor
     */
    constructor(scale: Double) : this(scale, scale, scale)

    /**
     * Creates a 4x4 transformation matrix equivalent to scaling in 3D
     *
     * @param v
     * vector scale factor
     */
    constructor(v: Vec3D) : this(v.x, v.y, v.z)

    /**
     * Creates a 4x4 transformation matrix equivalent to scaling in 3D
     *
     * @param x
     * x-axis scale factor
     * @param y
     * y-axis scale factor
     * @param z
     * z-axis scale factor
     */
    init {
        mat[0][0] = x
        mat[1][1] = y
        mat[2][2] = z
    }
}