package exceptions

open class WebGLException : Exception()

class ShaderNotCompiledException: WebGLException() {
    override fun toString(): String {
        return "Shader could not be compiled. Check the path to the shader program."
    }
}

class ShaderNotLoadedException: WebGLException() {
    override fun toString(): String {
        return "Shader could not been loaded."
    }
}

object ShaderProgramNotCreatedException: WebGLException()
object NotVertexShaderException: WebGLException()
object NotFragmentShaderException: WebGLException()
object AttributeBindException: WebGLException()
object ResourceNotFoundException : WebGLException()