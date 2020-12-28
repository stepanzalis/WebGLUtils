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
        webGl.detachShader(this, shader)
        if (webGl.isShader(shader)) {
            webGl.detachShader(this, shader)
        }
    }
}

fun WebGLProgram.bindUniformLocation(GL: WebGLRenderingContext, name: String, array: Float32Array, transpose: Boolean = false) {
    val matrixUniform = GL.getUniformLocation(this, name)
    GL.uniformMatrix4fv(matrixUniform, transpose, array)
}
