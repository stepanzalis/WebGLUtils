import kotlinx.browser.document

fun main() {
    val renderer: WebGlRenderer by lazy { WebGlRenderer() }

    document.body?.onload = {
        renderer.setup()
    }
}