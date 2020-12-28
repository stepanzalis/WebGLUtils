package transforms

/**
 * A 3x3 identity matrix
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
open class Mat3Identity : Mat3() {

    /**
     * Creates an identity 3x3 matrix
     */
    init {
        for (i in 0..2) mat[i][i] = 1.0
    }
}