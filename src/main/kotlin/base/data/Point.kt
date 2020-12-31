package base.data

// TODO
data class Point(val x: Float, val y: Float, val z: Float, val color: Color) {
    constructor(x: Number, y: Number, z: Number) :
            this(x.toFloat(), y.toFloat(), z.toFloat(), Color.randomColor())

    fun list() = listOf(x, y, z)

    data class Color(val r: Float, val g: Float, val b: Float, val a: Float) {
        constructor(r: Number, g: Number, b: Number, a: Number) :
                this(r.toFloat(), g.toFloat(), b.toFloat(), a.toFloat())

        companion object {
            fun randomColor() = Color(
                1,
                0.5,
                0.5,
                1.0
            )
        }

        fun list() = listOf(r, g, b, a)
    }
}