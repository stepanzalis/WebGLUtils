package base.ext

import base.BaseWebGlCanvas
import org.w3c.dom.HTMLButtonElement

fun HTMLButtonElement.setupCanvasOnClick(webGlCanvas: BaseWebGlCanvas, onElementShow: () -> Unit = {}) {
    addEventListener("click", {
        onElementShow.invoke()
        tryCatchWebGlException {
            webGlCanvas.setupResources()
        }
    })
}