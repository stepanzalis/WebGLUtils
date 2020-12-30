package base

import base.data.CanvasInfo
import base.data.MouseEventInfo
import base.data.ShaderType
import exceptions.NotFragmentShaderException
import exceptions.NotShaderException
import exceptions.ResourceNotFoundException
import exceptions.ShaderNotLoadedException
import kotlinx.browser.document
import kotlinx.browser.window
import org.khronos.webgl.WebGLRenderingContext as GL
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import kotlin.math.PI

abstract class BaseWebGlCanvas(
    private val canvasWidth: Int = DefaultWidth,
    private val canvasHeight: Int = DefaultHeight
) : MouseListener, KeyboardListener {

    private val canvas by lazy { document.getElementById(WebGlCanvasId) as HTMLCanvasElement }

    // Override these listeners if you want to use mouse or keyboard events
    override var mouseListener: ((Double, Double) -> Unit)? = { x, y -> }
    override val eventListener: EventListener = EventListener {}

    override val mouseEvent = MouseEventInfo()
    override val canvasSize = canvasInfo

    val resourceLoader by lazy { ResourceLoader() }
    val objFileLoader: WebGlObjectLoader by lazy { WebGlObjectLoader() }
    val canvasInfo get() = CanvasInfo(canvas.width, canvas.height)

    var webGl = canvas.getContext(WebGlContext) as GL
        private set

    init {
        setupCanvas()
        getWebGlVersion()
    }

    var time: Float = 0.0f
        private set

    fun incrementTime() {
        if (time == Float.MAX_VALUE) time = 0f // just to be sure
        time += 0.1f
    }

    private fun setupCanvas() = with(canvas) {
        height = canvasHeight
        width = canvasWidth
    }

    private fun getWebGlVersion() {
        val version = webGl.getParameter(GL.SHADING_LANGUAGE_VERSION)
        console.log("WebGL version: $version")
    }

    fun getShaderResource(name: String): String {
        if (ShaderType.isShader(name).not()) throw NotShaderException

        val resource = resourceLoader[name]
        console.log("Loading resource: $resource")
        return resource ?: throw ShaderNotLoadedException()
    }

    open fun loadResources(resources: Array<String> = emptyArray(), onLoadedResources: () -> Unit) =
        resourceLoader.loadResources(*resources, onLoadedResources = onLoadedResources)

    /**
     * Helper method for loading obj files to [WebGlObjectLoader]
     * @throws ResourceNotFoundException when resource is not loaded
     */
    fun loadModelObj(resourceName: String) {
        if (resourceLoader[resourceName] == null) throw ResourceNotFoundException
        objFileLoader.parseModelFromSource(resourceLoader[resourceName])
    }

    /** Setup function should be used for loading resources */
    abstract fun setupResources()

    /** After the resources are loaded, this functions is should be called */
    abstract fun render()

    /** Function that is called on every frame, usually called inside [renderInLoop] */
    abstract fun draw()

    /**
     * Rendering in loop
     * @param draw Function which will be called on every frame
     * */
    fun renderInLoop(draw: () -> Unit) = window.requestAnimationFrame { draw.invoke() }

    companion object {
        const val DefaultHeight = 600
        const val DefaultWidth = 800

        private const val WebGlContext = "webgl"
        private const val WebGlCanvasId = "canvas"
    }
}