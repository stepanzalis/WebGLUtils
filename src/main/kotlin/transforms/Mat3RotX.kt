package transforms

/**
 * A 3x3 matrix of right-handed rotation about x-axis
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat3RotX(alpha: Double) : Mat3Identity() {
    /**
     * Creates a 3x3 transformation matrix equivalent to right-handed rotation
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