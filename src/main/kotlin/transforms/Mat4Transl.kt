package transforms

/**
 * A 4x4 matrix of translation
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4Transl(x: Double, y: Double, z: Double) : Mat4Identity() {

    /**
     * Creates a 4x4 transformation matrix equivalent to translation in 3D
     *
     * @param v
     * translation vector
     */
    constructor(v: Vec3D) : this(v.x, v.y, v.z)

    /**
     * Creates a 4x4 transformation matrix equivalent to translation in 3D
     *
     * @param x
     * translation along x-axis
     * @param y
     * translation along y-axis
     * @param z
     * translation along z-axis
     */
    init {
        mat[3][0] = x
        mat[3][1] = y
        mat[3][2] = z
    }
}