package transforms

/**
 * A 3x3 matrix of 2D translation
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat3Transl2D(x: Double, y: Double) : Mat3Identity() {

    /**
     * Creates a 3x3 transformation matrix equivalent to translation in 2D
     *
     * @param v
     * translation vector
     */
    constructor(v: Vec2D) : this(v.x, v.y)

    /**
     * Creates a 3x3 transformation matrix equivalent to translation in 2D
     *
     * @param x
     * translation along x-axis
     * @param y
     * translation along y-axis
     */
    init {
        mat[2][0] = x
        mat[2][1] = y
    }
}