package base

import base.data.CanvasInfo
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

abstract class BaseWebGlCanvas(
    private val canvasWidth: Int = DefaultWidth,
    private val canvasHeight: Int = DefaultHeight
) {
    private val canvas by lazy { document.getElementById(WebGlCanvasId) as HTMLCanvasElement }

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

    open fun initKeyboardListeners() = document.addEventListener("keyup", eventListener, false)
    open val eventListener: EventListener = EventListener {}

    /**
     * Helper method for loading obj files to [WebGlObjectLoader]
     * @throws ResourceNotFoundException when resource is not loaded
     */
    fun loadModelObj(resourceName: String) {
        if (resourceLoader[resourceName] == null) throw ResourceNotFoundException
        objFileLoader.parseModelFromSource(resourceLoader[resourceName])
    }

    /** Setup function should be used for loading resources */
    abstract fun setup()

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
        private const val DefaultHeight = 600
        private const val DefaultWidth = 800

        private const val WebGlContext = "webgl"
        private const val WebGlCanvasId = "canvas"
    }
}