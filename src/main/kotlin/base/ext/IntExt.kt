package base.ext

fun Int.isPowerOf2() = this > 0 && (this and this-1 == 0)