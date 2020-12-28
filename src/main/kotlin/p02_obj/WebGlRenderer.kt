package p02_obj

import base.*
import base.data.WebGlAttribute
import base.data.WebGlBuffer
import ext.*
import org.khronos.webgl.*
import transforms.*
import kotlin.math.PI
import org.khronos.webgl.WebGLRenderingContext as GL

class WebGlRenderer : BaseWebGlCanvas() {

    private var shaderProgram: WebGLProgram? = null

    private val projMat = Mat4PerspRH(PI / 3, 1.0, 0.1, 60.0)
    var camera: Camera = Camera().withPosition(Vec3D(20.0, 20.0, 20.0))
        .withAzimuth(PI * 1.25)
        .withZenith(PI * -0.125)

    override fun setup() {
        shaderProgram = webGl.createProgram()

        val resources = arrayOf("obj-vertex-shader.vert", "obj-frag-shader.frag", "teapot.obj")
        loadResources(resources) {
            render()
        }
    }

    private var rotation = 0.0
    private var rotationSpeed = 0.1

    private fun draw() {
        with(webGl) {
            clearColorBuffer()
            initViewport(canvasInfo.width, canvasInfo.height)

            rotation -= rotationSpeed

            val modMat = Mat4RotY(rotation).mul(Mat4RotX(PI / 2))

            shaderProgram?.bindUniformLocation(webGl, "uPMatrix", projMat.floatArray())
            shaderProgram?.bindUniformLocation(webGl, "uVMatrix", camera.viewMatrix.floatArray())
            shaderProgram?.bindUniformLocation(webGl, "uMMatrix", modMat.floatArray())

            drawElements(GL.TRIANGLES, objFileLoader.getNumFaces() * 3, GL.UNSIGNED_SHORT, 0)
            renderInLoop { draw() }
        }
    }

    override fun render() = with(webGl) {
        loadModelObj("teapot.obj")

        enable(GL.DEPTH_TEST)
        compileShaderProgram()

        val buffer = WebGlBuffer(
            indices = objFileLoader.getFacesArray(),
            vertices = objFileLoader.getVerticesArray(),
            attributes = arrayOf(
                WebGlAttribute("aVertexPosition", 3),
                WebGlAttribute("aVertexNormal", 3),
            ),
        )

        initBuffer(shaderProgram, buffer)
        draw()
    }

    private fun compileShaderProgram() {
        val shaders = ShaderProgram.loadShaders(webGl, resourceLoader, "obj-vertex-shader.vert", "obj-frag-shader.frag")
        shaderProgram.attachShaders(webGl, shaders)
        webGl.linkProgram(shaderProgram)
        webGl.useProgram(shaderProgram)
        shaderProgram.detachShaders(webGl, shaders)
    }
}