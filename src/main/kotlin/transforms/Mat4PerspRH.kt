package transforms

/**
 * A 4x4 matrix of right-handed perspective visibility volume to normalized
 * clipping volume transformation
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4PerspRH(
    alpha: Double, k: Double, zn: Double,
    zf: Double
) : Mat4Identity() {
    /**
     * Creates a 4x4 transformation matrix equivalent to the mapping of an
     * perspective visibility volume (an axis-aligned frustum symmetrical about
     * xz and yz planes) of given dimensions to the normalized clipping volume
     * ([-1,1]x[-1,1]x[0,1])
     *
     * @param alpha
     * vertical field of view angle in radians
     * @param k
     * volume height/width ratio
     * @param zn
     * distance to the near clipping plane along z-axis
     * @param zf
     * distance to the far clipping plane along z-axis
     */
    init {
        val h: Double = 1.0 / kotlin.math.tan(alpha / 2.0)
        val w = k * h
        mat[0][0] = w
        mat[1][1] = h
        mat[2][2] = zf / (zn - zf)
        mat[3][2] = zn * zf / (zn - zf)
        mat[2][3] = -1.0
        mat[3][3] = 0.0
    }
}