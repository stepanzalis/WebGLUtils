package p03_cube

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

    private var rotation = .0

    private val cube by lazy { CubeGeometry() }
    private var shaderProgram: WebGLProgram? = null

    private val projMat = Mat4PerspRH(PI / 4, 1.0, 0.01, 60.0)

    var camera = Camera()
        .withPosition(Vec3D(5.0, 5.0, 2.5))
        .withAzimuth(PI * 1.25)
        .withZenith(PI * -0.125)

    override fun setupResources() {
        shaderProgram = webGl.createProgram()

        val resources = arrayOf("cube_shader.vert", "cube_shader.frag")
        loadResources(resources) {
            render()
        }
    }

    override var mouseListener: ((Double, Double) -> Unit)? = { x, y ->
        camera = camera
            .addAzimuth(PI * x / canvasInfo.width)
            .addZenith(PI * y / canvasInfo.width)
    }

    override val eventListener = EventListener { event ->
        when (event) {
            is KeyboardEvent -> {
                when (event.key) {
                    "w" -> camera = camera.forward(1.0)
                    "s" -> camera = camera.backward(1.0)
                    "d" -> camera = camera.right(1.0)
                    "a" -> camera = camera.left(1.0)
                }
            }
        }
    }

    override fun draw() {
        with(webGl) {
            clearColorBuffer()

            rotation -= RotationSpeed

            val mMat = Mat4RotZ(rotation)

            shaderProgram?.bindUniformMatrix4fv(webGl, "uModelMatrix", mMat.floatArray())
            shaderProgram?.bindUniformMatrix4fv(webGl, "uViewMatrix", camera.viewMatrix.floatArray())
            shaderProgram?.bindUniformMatrix4fv(webGl, "uProjectionMatrix", projMat.floatArray())

            drawElements(GL.TRIANGLES, cube.indices.size, GL.UNSIGNED_SHORT, 0)
            renderInLoop { draw() }
        }
    }

    override fun render() = with(webGl) {
        enable(GL.DEPTH_TEST)
        compileShaderProgram()

        initKeyboardListeners()
        initMouseListeners()

        val buffer = WebGlBuffer(
            indices = cube.indices,
            colors = cube.colors,
            vertices = cube.vertices,
            attributes = arrayOf(
                WebGlAttribute("aVertexPosition", 3),
                WebGlAttribute("aColor", 3),
            ),
        )

        initBuffer(shaderProgram, buffer)
        initViewport(canvasInfo.width, canvasInfo.height)

        draw()
    }

    private fun compileShaderProgram() {
        val shaders = ShaderProgram.loadShaders(
            webGl,
            resourceLoader,
            shaderName = "cube_shader",
        )

        shaderProgram.useShaders(webGl, shaders)
    }

    companion object {

        // Constants
        private const val RotationSpeed = 0.02
    }
}