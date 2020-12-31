package ext

import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLShader

fun WebGLProgram?.attachShaders(webGl: WebGLRenderingContext, shaders: List<WebGLShader>) {
    console.log("Attaching shaders to program.")
    shaders.forEach { attachShader(webGl, it) }
}

fun WebGLProgram?.attachShader(webGl: WebGLRenderingContext, shader: WebGLShader) = webGl.attachShader(this, shader)

fun WebGLProgram?.detachShaders(webGl: WebGLRenderingContext, shaders: List<WebGLShader>) {
    console.log("Detaching shaders from program.")

    shaders.forEach { shader ->
        if (webGl.isShader(shader)) {
            webGl.detachShader(this, shader)
        }
    }
}

fun WebGLProgram?.useShaders(webGl: WebGLRenderingContext, shaders: List<WebGLShader>) {
    attachShaders(webGl, shaders)
    webGl.linkProgram(this)
    webGl.useProgram(this)
    detachShaders(webGl, shaders)
}

fun WebGLProgram.bindUniformMatrix4fv(
    GL: WebGLRenderingContext,
    name: String,
    array: Float32Array,
    transpose: Boolean = false
) {
    val matrixUniform = GL.getUniformLocation(this, name)
    GL.uniformMatrix4fv(matrixUniform, transpose, array)
}

fun WebGLProgram.bindUniformMatrix4fv(
    GL: WebGLRenderingContext,
    uniforms: List<Pair<String, Float32Array>>,
    transpose: Boolean = false
) {
    uniforms.forEach { bindUniformMatrix4fv(GL, it.first, it.second, transpose) }
}

fun WebGLProgram.bindUniformMatrix3fv(
    GL: WebGLRenderingContext,
    name: String,
    array: Array<Float>,
    transpose: Boolean = false
) {
    val loc = GL.getUniformLocation(this, name)
    GL.uniformMatrix3fv(loc, transpose, array)
}

fun WebGLProgram.bindUniformMatrix3fv(
    GL: WebGLRenderingContext,
    name: String,
    array: Float32Array,
    transpose: Boolean = false
) {
    val loc = GL.getUniformLocation(this, name)
    GL.uniformMatrix3fv(loc, transpose, array)
}

fun WebGLProgram.bindUniform3fv(GL: WebGLRenderingContext, name: String, array: Array<Float>) {
    val loc = GL.getUniformLocation(this, name)
    GL.uniform3fv(loc, array)
}

fun WebGLProgram.bindUniform3fv(GL: WebGLRenderingContext, uniforms: List<Pair<String, Array<Float>>>) {
    uniforms.forEach {
        val loc = GL.getUniformLocation(this, it.first)
        GL.uniform3fv(loc, it.second)
    }
}

fun WebGLProgram.bindUniform2fv(GL: WebGLRenderingContext, name: String, array: Array<Float>) {
    val loc = GL.getUniformLocation(this, name)
    GL.uniform2fv(loc, array)
}

fun WebGLProgram.bindUniformValue1f(GL: WebGLRenderingContext, name: String, value: Float) {
    val loc = GL.getUniformLocation(this, name)
    GL.uniform1f(loc, value)
}