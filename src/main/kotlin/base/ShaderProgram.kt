package base

import base.data.ShaderType
import exceptions.*
import org.khronos.webgl.WebGLRenderingContext as GL
import org.khronos.webgl.WebGLShader

/**
 * Utility class to create, compile and link a shader
 *
 * @throws ShaderNotCompiledException if shader can not parse a compiled shader
 * @return WebGLShader
 */
object ShaderProgram {

    /**
     * @param vertexShaderRes Link to the resource folder of vertex shader
     * @param resourceLoader Class to load shader's code
     * @param fragmentShaderRes Link to the resource folder of fragment shader
     * @return List of compiled shaders
     */
    fun loadShaders(
        webGl: GL,
        resourceLoader: ResourceLoader,
        vertexShaderRes: String,
        fragmentShaderRes: String
    ): List<WebGLShader> {
        ShaderTypeErrorHandler.checkShaderResources(vertexShaderRes, fragmentShaderRes)

        val vs = getVertexShader(webGl, resourceLoader[vertexShaderRes] ?: throw ShaderNotLoadedException())
        val fs = getFragmentShader(webGl, resourceLoader[fragmentShaderRes] ?: throw ShaderNotLoadedException())
        return listOf(vs, fs)
    }

    /**
     * @param resourceLoader Class to load shader's code
     * @param shaderName Name of the vertex and fragment shader program without suffix
     * @return List of compiled shaders
     */
    fun loadShaders(webGl: GL, resourceLoader: ResourceLoader, shaderName: String): List<WebGLShader> {
        val vertexShader = "${shaderName}${ShaderType.Vertex.suffix}"
        val fragmentShader = "${shaderName}${ShaderType.Fragment.suffix}"
        return loadShaders(webGl, resourceLoader, vertexShader, fragmentShader)
    }

    /**
     * @param source GLSL source code to compile
     */
    fun getFragmentShader(webGl: GL, source: String): WebGLShader = getShader(webGl, source, GL.FRAGMENT_SHADER)

    /**
     * @param source GLSL source code to compile
     */
    fun getVertexShader(webGl: GL, source: String): WebGLShader = getShader(webGl, source, GL.VERTEX_SHADER)

    private fun getShader(webGl: GL, shaderSource: String, shaderType: Int): WebGLShader {
        val shader = webGl.createShader(shaderType).apply {
            webGl.shaderSource(this, shaderSource)
            webGl.compileShader(this)
        }

        checkShaderCompilation(webGl, shader)
        return shader ?: throw ShaderNotCompiledException()
    }

    /**
     * Checks if shader can be compiled
     *
     * When compilation is not success, the shader is deleted to free resources.
     * @throws ShaderProgramNotCreatedException if shader is null
     */
    private fun checkShaderCompilation(webGl: GL, shader: WebGLShader?) {
        shader?.let {
            if (webGl.getShaderParameter(shader, GL.COMPILE_STATUS) == false) {
                console.log(webGl.getShaderInfoLog(shader))
                // We don't need the program anymore
                webGl.deleteShader(it)
            }
        } ?: throw ShaderProgramNotCreatedException
    }
}

object ShaderTypeErrorHandler {

    /**
     * Check if provided resources are in right order
     *
     * @throws NotVertexShaderException If provided resource has not [ShaderType.Vertex.suffix]
     * @throws NotFragmentShaderException If provided resource has not [ShaderType.Fragment.suffix]
     */
    fun checkShaderResources(vertexShaderRes: String, fragmentShaderRes: String) {
        when {
            vertexShaderRes.contains(ShaderType.Vertex.suffix).not() -> throw NotVertexShaderException
            fragmentShaderRes.contains(ShaderType.Fragment.suffix).not() -> throw NotFragmentShaderException
        }
    }
}