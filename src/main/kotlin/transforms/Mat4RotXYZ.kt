package transforms

/**
 * A 4x4 matrix of sequential right-handed rotation about x, y and z axes
 *
 *
 * Creates a 4x4 transformation matrix equivalent to right-handed rotations
 * about x, y and z axes chained in sequence in this order
 *
 * @param alpha
 * rotation angle about x-axis, in radians
 * @param beta
 * rotation angle about y-axis, in radians
 * @param gamma
 * rotation angle about z-axis, in radians
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat4RotXYZ(alpha: Double, beta: Double, gamma: Double) : Mat4(
    Mat4RotX(alpha).mul(Mat4RotY(beta)).mul(
        Mat4RotZ(gamma)
    )
)