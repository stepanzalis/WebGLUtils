package p01_triangle

import base.*
import base.data.WebGlAttribute
import base.data.WebGlBuffer
import ext.*
import org.khronos.webgl.*
import org.khronos.webgl.WebGLRenderingContext as GL

class WebGlRenderer : BaseWebGlCanvas() {

    private var shaderProgram: WebGLProgram? = null

    override fun setup() {
        shaderProgram = webGl.createProgram()

        val resources = arrayOf("attr_shader.vert", "attr_shader.frag")
        loadResources(resources) {
            render()
        }
    }

    override fun draw() {
        with(webGl) {
            clearColorBuffer()

            val buffer = WebGlBuffer(
                indices = arrayOf(0, 1, 2),
                attributes = arrayOf(
                    WebGlAttribute("inPosition", 2, 20),
                    WebGlAttribute("inColor", 3, 20, 8)
                ),
                vertices = arrayOf(
                    -1f, -1f, 	    0.7f, 0f, 0f,
                     1f,  0f,		0f, 0.7f, 0f,
                     0f,  1f,		0f, 0f, 0.7f
                )
            )

            val locTime = initUniformLoc(shaderProgram, "time")
            initBuffer(shaderProgram, buffer)

            incrementTime()
            uniform1f(locTime, time)

            drawArrays(GL.TRIANGLES, 0, 3)
            renderInLoop { draw() }
        }
    }

    override fun render() = with(webGl) {
        compileShaderProgram()
        initViewport(canvasInfo.width, canvasInfo.height)
        draw()
    }

    private fun compileShaderProgram() {
        val shaders = ShaderProgram.loadShaders(webGl, resourceLoader, "attr_shader.vert", "attr_shader.frag")
        shaderProgram.attachShaders(webGl, shaders)
        webGl.linkProgram(shaderProgram)
        webGl.useProgram(shaderProgram)
        shaderProgram.detachShaders(webGl, shaders)
    }
}