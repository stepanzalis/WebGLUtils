import ext.setupCanvasOnClick
import kotlinx.browser.document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import p01_triangle.WebGlRenderer as TriangleWebGlRenderer
import p02_obj.WebGlRenderer as ObjectWebGlRenderer

fun main() {
    // HTML elements
    val canvas by lazy { document.getElementById("canvas") as HTMLElement }
    val controls by lazy { document.getElementById("controls") as HTMLElement }

    val triangleButton by lazy { document.getElementById("triangle") as HTMLButtonElement }
    val objectButton by lazy { document.getElementById("object") as HTMLButtonElement }

    // Renderers
    val triangleRenderer: TriangleWebGlRenderer by lazy { TriangleWebGlRenderer() }
    val objectRenderer: ObjectWebGlRenderer by lazy { ObjectWebGlRenderer() }

    document.body?.onload = {
        val elements = arrayOf(canvas, controls)
        toggleElementsVisibility(elements, hidden = true)

        triangleButton.setupCanvasOnClick(triangleRenderer, onElementShow = { toggleElementsVisibility(arrayOf(canvas),false) })
        objectButton.setupCanvasOnClick(objectRenderer, onElementShow = { toggleElementsVisibility(elements,false)  })
    }
}

private fun toggleElementsVisibility(array: Array<HTMLElement>, hidden: Boolean) {
    array.forEach { it.hidden = hidden }
}

