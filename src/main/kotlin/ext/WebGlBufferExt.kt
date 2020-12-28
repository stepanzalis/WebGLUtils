package ext

import base.data.WebGlAttribute
import base.data.WebGlBuffer
import exceptions.AttributeBindException
import org.khronos.webgl.*
import org.khronos.webgl.WebGLRenderingContext as GL

/**
 * Creates and binds buffer to a [program]
 * @return Vertex and fragment buffer
 */
fun GL.initBuffer(program: WebGLProgram?, buffer: WebGlBuffer): List<WebGLBuffer?> {
    val vertexBuffer = initBuffer(this)
    bufferData(GL.ARRAY_BUFFER, Float32Array(buffer.vertices), GL.STATIC_DRAW)

    val indexBuffer = initBuffer(this, GL.ELEMENT_ARRAY_BUFFER)
    bufferData(GL.ELEMENT_ARRAY_BUFFER, Uint16Array(buffer.indices), GL.STATIC_DRAW)

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
private fun initBuffer(webGl: GL, bufferType: Int = GL.ARRAY_BUFFER): WebGLBuffer? {
    val buffer = webGl.createBuffer()
    webGl.bindBuffer(bufferType, buffer)
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