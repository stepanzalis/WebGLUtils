package base.data

data class Face(val p1: Short, val p2: Short, val p3: Short) {
    constructor(p1: Number, p2: Number, p3: Number) :
            this(p1.toShort(), p2.toShort(), p3.toShort())

    fun list() = listOf(p1, p2, p3)
}