package transforms

/**
 * A 3x3 matrix of right-handed rotation about z-axis
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat3Rot2D(alpha: Double) : Mat3Identity() {
    /**
     * Creates a 3x3 transformation matrix equivalent to right-handed rotation
     * about z-axis
     *
     * @param alpha
     * rotation angle in radians
     */
    init {
        mat[0][0] = kotlin.math.cos(alpha)
        mat[1][1] = kotlin.math.cos(alpha)
        mat[1][0] = -kotlin.math.sin(alpha)
        mat[0][1] = kotlin.math.sin(alpha)
    }
}