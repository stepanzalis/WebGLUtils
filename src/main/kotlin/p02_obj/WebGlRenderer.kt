package p02_obj

import base.*
import base.data.WebGlAttribute
import base.data.WebGlBuffer
import ext.*
import kotlinx.browser.document
import org.khronos.webgl.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.KeyboardEvent
import transforms.*
import kotlin.math.PI
import org.khronos.webgl.WebGLRenderingContext as GL

class WebGlRenderer : BaseWebGlCanvas() {

    private var animated: Boolean = true
    private var rotation = .0
    private var shininess = InitialShininess

    private var shaderProgram: WebGLProgram? = null

    // Shininess value
    private val shininessInput = document.getElementById("shininess") as HTMLInputElement

    // Animated checkbox
    private val animatedCheckbox = document.getElementById("animated") as HTMLInputElement

    // Light position
    private val lightPosXInput = document.getElementById("lightPosX") as HTMLInputElement
    private val lightPosYInput = document.getElementById("lightPosY") as HTMLInputElement
    private val lightPosZInput = document.getElementById("lightPosZ") as HTMLInputElement

    private var lightPos = arrayOf(
        lightPosXInput.valueAsNumber.toFloat(),
        lightPosYInput.valueAsNumber.toFloat(),
        lightPosZInput.valueAsNumber.toFloat()
    )

    init {
        // Light position
        lightPosXInput.oninput = { lightPos[0] = lightPosXInput.valueAsNumber.toFloat(); null }
        lightPosYInput.oninput = { lightPos[1] = lightPosYInput.valueAsNumber.toFloat(); null }
        lightPosZInput.oninput = { lightPos[2] = lightPosZInput.valueAsNumber.toFloat(); null }

        // Animated
        animatedCheckbox.onchange = { animated = animatedCheckbox.checked; null }

        // Shininess
        shininessInput.oninput = { shininess = shininessInput.valueAsNumber.toFloat(); null }
    }

    private val projMat = Mat4PerspRH(PI / 3, 1.0, 1.0, 60.0)
    var camera = Camera()
        .withPosition(Vec3D(20.0, 20.0, 10.0))
        .withAzimuth(PI * 1.25)
        .withZenith(PI * -0.125)

    override fun setupResources() {
        shaderProgram = webGl.createProgram()

        val resources = arrayOf("obj_shader.vert", "obj_shader.frag", "teapot.obj")
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

            // Control rotation (enabled / disabled)
            if (animated) {
                rotation -= RotationSpeed
            }

            val modMat = Mat4RotY(rotation).mul(Mat4RotX(PI / 2))
            val norMat = Mat3(modMat.mul(camera.viewMatrix))

            shaderProgram?.bindUniformValue1f(webGl, "uShininess", shininess)
            shaderProgram?.bindUniform3fv(webGl, "uLightPos", lightPos)
            shaderProgram?.bindUniformMatrix3fv(webGl, "uNMatrix", norMat.floatArray())

            shaderProgram?.bindUniformMatrix4fv(
                webGl,
                listOf(
                    "uPMatrix" to projMat.floatArray(),
                    "uVMatrix" to camera.viewMatrix.floatArray(),
                    "uMMatrix" to modMat.floatArray()
                )
            )

            drawElements(GL.TRIANGLES, objFileLoader.getNumFaces() * 3, GL.UNSIGNED_SHORT, 0)
            renderInLoop { draw() }
        }
    }

    override fun render() = with(webGl) {
        loadModelObj("teapot.obj")

        enable(GL.DEPTH_TEST)
        compileShaderProgram()

        initKeyboardListeners()
        initMouseListeners()

        val ambientColor = arrayOf(0.1f, 0.1f, 0.1f)
        val diffuseColor = arrayOf(0.4f, 0.4f, 0.0f)
        val specularColor = arrayOf(1f, 1f, 1f)

        shaderProgram?.bindUniform3fv(
            webGl,
            listOf(
                "uLightPos" to lightPos,
                "uAmbientColor" to ambientColor,
                "uDiffuseColor" to diffuseColor,
                "uSpecularColor" to specularColor
            )
        )

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
        val shaders = ShaderProgram.loadShaders(
            webGl,
            resourceLoader,
            vertexShaderRes = "obj_shader.vert",
            fragmentShaderRes = "obj_shader.frag"
        )

        shaderProgram.useShaders(webGl, shaders)
    }

    companion object {
        // Constants
        private const val RotationSpeed = 0.05
        private const val InitialShininess = 0.9f
    }
}