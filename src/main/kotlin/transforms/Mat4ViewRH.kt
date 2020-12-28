package transforms


/**
 * A 4x4 matrix of right-handed view transformation
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4ViewRH(e: Vec3D, v: Vec3D, u: Vec3D) : Mat4Identity() {

    /**
     * Creates a 4x4 transition matrix from the current frame (coordinate
     * system) to the observer (camera) frame described with respect to the
     * current frame by origin position, view vector and up vector. The inherent
     * observer frame is constructed orthonormal, specifically in {o, {x, y, z}}
     * notation it is
     * {e, {normalize(u cross -v), normalize(-v cross (u cross -v)), normalize(-v)}}
     * where cross is the cross-product and normalize returns a collinear unit vector
     * (by dividing all vector components by vector length)
     *
     * @param e
     * eye, position of the observer frame origin with respect to the
     * current frame
     * @param v
     * view vector, the direction of the observer frame -z axis with
     * respect to the current frame
     * @param u
     * up vector, together with eye and view vector defines with
     * respect to the current frame the plane perpendicular to the
     * observer frame x axis (i.e. the plane in which lies the
     * observer frame y axis)
     */
    init {
        val x: Vec3D
        val y: Vec3D
        val z: Vec3D = v.mul(-1.0).normalized() ?: (Vec3D(1.0, 0.0, 0.0))
        x = u.cross(z).normalized() ?: (Vec3D(1.0, 0.0, 0.0))
        y = z.cross(x)
        mat[0][0] = x.x
        mat[1][0] = x.y
        mat[2][0] = x.z
        mat[3][0] = -e.dot(x)
        mat[0][1] = y.x
        mat[1][1] = y.y
        mat[2][1] = y.z
        mat[3][1] = -e.dot(y)
        mat[0][2] = z.x
        mat[1][2] = z.y
        mat[2][2] = z.z
        mat[3][2] = -e.dot(z)
    }
}