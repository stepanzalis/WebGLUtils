package transforms

/**
 * A 4x4 matrix of right-handed rotation about y-axis
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4RotY(alpha: Double) : Mat4Identity() {
    /**
     * Creates a 4x4 transformation matrix equivalent to right-handed rotation
     * about y-axis
     *
     * @param alpha
     * rotation angle in radians
     */
    init {
        mat[0][0] = kotlin.math.cos(alpha)
        mat[2][2] = kotlin.math.cos(alpha)
        mat[2][0] = kotlin.math.sin(alpha)
        mat[0][2] = -kotlin.math.sin(alpha)
    }
}