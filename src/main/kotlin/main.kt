import ext.tryCatchWebGlException
import kotlinx.browser.document
import p02_obj.WebGlRenderer

fun main() {
    val renderer: WebGlRenderer by lazy { WebGlRenderer() }

    document.body?.onload = {
        tryCatchWebGlException {
            renderer.setup()
        }
    }
}