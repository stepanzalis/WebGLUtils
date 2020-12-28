package transforms

/**
 * A 4x4 matrix of right-handed rotation about x-axis
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4RotX(alpha: Double) : Mat4Identity() {
    /**
     * Creates a 4x4 transformation matrix equivalent to right-handed rotation
     * about x-axis
     *
     * @param alpha
     * rotation angle in radians
     */
    init {
        mat[1][1] = kotlin.math.cos(alpha)
        mat[2][2] = kotlin.math.cos(alpha)
        mat[2][1] = -kotlin.math.sin(alpha)
        mat[1][2] = kotlin.math.sin(alpha)
    }
}