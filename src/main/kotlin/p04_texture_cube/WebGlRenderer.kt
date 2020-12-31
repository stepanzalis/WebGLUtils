package p04_texture_cube

import base.*
import base.data.WebGlAttribute
import base.data.WebGlBuffer
import base.ext.*
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

        val resources = arrayOf("$ShaderProgramName.vert", "$ShaderProgramName.frag")
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

    override fun render(): Unit = with(webGl) {
        enable(GL.DEPTH_TEST)
        clearDepth(1.0f)

        compileShaderProgram()

        initKeyboardListeners()
        initMouseListeners()

        val buffer = WebGlBuffer(
            indices = cube.indices,
            vertices = cube.vertices,
            textureCoord = cube.textureCoord,
            attributes = arrayOf(
                WebGlAttribute("aVertexPosition", 3),
                WebGlAttribute("aTextureCoord", 2),
            ),
        )

        initBuffer(shaderProgram, buffer)
        initViewport(canvasInfo.width, canvasInfo.height)

        loadImageTexture(TextureName, onTextureLoaded = {
            it?.let {
                it.useTexture(webGl)
                val sampler = initUniformLoc(shaderProgram, "uSampler")
                uniform1i(sampler, 0)

                draw()
            }
        })
    }

    private fun compileShaderProgram() {
        val shaders = ShaderProgram.loadShaders(
            webGl,
            resourceLoader,
            shaderName = ShaderProgramName,
        )

        shaderProgram.useShaders(webGl, shaders)
    }

    companion object {

        // Constants
        private const val RotationSpeed = 0.02
        private const val TextureName = "bricks.png"
        private const val ShaderProgramName = "cube_texture_shader"
    }
}