package transforms


/**
 * A 4x4 identity matrix
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
open class Mat4Identity : Mat4() {

    /**
     * Creates an identity 4x4 matrix
     */
    init {
        for (i in 0..3) mat[i][i] = 1.0
    }
}