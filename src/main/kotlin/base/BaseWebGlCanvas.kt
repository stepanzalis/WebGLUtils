package base

import base.data.CanvasInfo
import exceptions.ShaderNotLoadedException
import kotlinx.browser.document
import kotlinx.browser.window
import org.khronos.webgl.WebGLRenderingContext as GL
import org.w3c.dom.HTMLCanvasElement

abstract class BaseWebGlCanvas(
    private val canvasWidth: Int = DefaultWidth,
    private val canvasHeight: Int = DefaultHeight
) {
    val resourceLoader by lazy { ResourceLoader() }
    private val canvas by lazy { document.getElementById(WebGlCanvasId) as HTMLCanvasElement }

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
        val resource = resourceLoader[name]
        console.log("Loading resource: $resource")
        return resource ?: throw ShaderNotLoadedException()
    }

    fun renderInLoop(draw: () -> Unit) {
        window.requestAnimationFrame { draw.invoke() }
    }

    open fun loadResources(resources: Array<String> = emptyArray(), onLoadedResources: () -> Unit) =
        resourceLoader.loadResources(*resources, onLoadedResources = onLoadedResources)

    abstract fun setup()
    abstract fun render()

    companion object {
        private const val DefaultHeight = 600
        private const val DefaultWidth = 800

        private const val WebGlContext = "webgl"
        private const val WebGlCanvasId = "canvas"
    }
}