package transforms

/**
 * A 3x3 matrix of 2D scaling
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Mat3Scale2D(x: Double, y: Double) : Mat3Identity() {

    /**
     * Creates a 3x3 transformation matrix equivalent to uniform scaling in 2D
     *
     * @param scale
     * x,y -axis scale factor
     */
    constructor(scale: Double) : this(scale, scale)

    /**
     * Creates a 3x3 transformation matrix equivalent to scaling in 2D
     *
     * @param v
     * vector scale factor
     */
    constructor(v: Vec2D) : this(v.x, v.y)

    /**
     * Creates a 3x3 transformation matrix equivalent to scaling in 2D
     *
     * @param x
     * x-axis scale factor
     * @param y
     * y-axis scale factor
     */
    init {
        mat[0][0] = x
        mat[1][1] = y
    }
}