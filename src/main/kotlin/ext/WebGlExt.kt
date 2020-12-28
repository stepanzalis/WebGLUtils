package ext

import base.ShaderProgram
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext

fun WebGLRenderingContext.clearColorBuffer(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) {
    clearColor(red, green, blue, alpha)
    clear(WebGLRenderingContext.COLOR_BUFFER_BIT)
}

fun WebGLRenderingContext.initViewport(width: Int, height: Int) = viewport(0, 0, width, height)

fun WebGLRenderingContext.initUniformLoc(program: WebGLProgram?, name: String) =
    program?.let { getUniformLocation(program, name) }