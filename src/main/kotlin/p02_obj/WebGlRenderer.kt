package p02_obj

import base.*
import base.data.WebGlAttribute
import base.data.WebGlBuffer
import ext.*
import org.khronos.webgl.*
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.KeyboardEvent
import transforms.*
import kotlin.math.PI
import org.khronos.webgl.WebGLRenderingContext as GL

class WebGlRenderer : BaseWebGlCanvas() {

    private var rotation = 0.0
    private var rotationSpeed = 0.05

    private var shaderProgram: WebGLProgram? = null
    private val projMat = Mat4PerspRH(PI / 3, 1.0, 0.1, 60.0)
    var camera = Camera()
        .withPosition(Vec3D(20.0, 20.0, 20.0))
        .withAzimuth(PI * 1.25)
        .withZenith(PI * -0.125)

    override fun setup() {
        shaderProgram = webGl.createProgram()

        val resources = arrayOf("obj-vertex-shader.vert", "obj-frag-shader.frag", "teapot.obj")
        loadResources(resources) {
            render()
        }
    }

    override val eventListener = EventListener { event ->
        val e = event as? KeyboardEvent
        when (e?.key) {
            "w" -> camera = camera.forward(1.0)
            "s" -> camera = camera.backward(1.0)
            "d" -> camera = camera.right(1.0)
            "a" -> camera = camera.left(1.0)
        }
    }

    override fun draw() {
        with(webGl) {
            clearColorBuffer()
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
        initKeyboardListeners()

        val buffer = WebGlBuffer(
            indices = objFileLoader.getFacesArray(),
            vertices = objFileLoader.getVerticesArray(),
            attributes = arrayOf(
                WebGlAttribute("aVertexPosition", 3),
                WebGlAttribute("aVertexNormal", 3),
            ),
        )

        initBuffer(shaderProgram, buffer)
        initViewport(canvasInfo.width, canvasInfo.height)

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