package ext

import base.data.WebGlAttribute
import base.data.WebGlBuffer
import exceptions.AttributeBindException
import org.khronos.webgl.Float32Array
import org.khronos.webgl.Uint32Array
import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext as GL

/**
 * Creates and binds buffer to a [program]
 * @return Vertex and fragment buffer
 */
fun GL.createBuffers(program: WebGLProgram?, buffer: WebGlBuffer): List<WebGLBuffer?> {
    val vertexBuffer = createBuffer(this)
    bufferData(GL.ARRAY_BUFFER, Float32Array(buffer.vertices), GL.STATIC_DRAW)

    val indexBuffer = createBuffer(this)
    bufferData(GL.ELEMENT_ARRAY_BUFFER, Uint32Array(buffer.indices), GL.STATIC_DRAW)

    // Bind buffers
    bindBuffer(GL.ARRAY_BUFFER, vertexBuffer)
    bindBuffer(GL.ELEMENT_ARRAY_BUFFER, indexBuffer)

    // Init attributes
    bindAttributes(this, program, buffer)
    return (listOf(vertexBuffer, indexBuffer))
}

/**
 * Create and bind buffer to context
 */
private fun createBuffer(webGl: GL): WebGLBuffer? {
    val buffer = webGl.createBuffer()
    webGl.bindBuffer(GL.ARRAY_BUFFER, webGl.createBuffer())
    return buffer
}

/**
 * Bind attribute to a shader program
 */
private fun bindAttributes(webGl: GL, program: WebGLProgram?, buffer: WebGlBuffer) {
    program?.let {
        buffer.attributes.forEach { attribute ->
            bindAttribute(webGl, program, attribute)
        }
    } ?: throw AttributeBindException
}

private fun bindAttribute(webGl: GL, program: WebGLProgram?, attribute: WebGlAttribute) = with(webGl) {
    val attr = getAttribLocation(program, attribute.name)
    vertexAttribPointer(attr, attribute.dimension, GL.FLOAT, false, attribute.stride, attribute.offset)
    enableVertexAttribArray(attr)
}