package ext

import org.khronos.webgl.Float32Array
import org.khronos.webgl.Uint16Array

fun List<Float>.toFloat32Array() = Float32Array(this.toTypedArray())
fun List<Short>.toUint16Array() = Uint16Array(this.toTypedArray())