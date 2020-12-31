import base.ext.setupCanvasOnClick
import kotlinx.browser.document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import p01_triangle.WebGlRenderer as TriangleWebGlRenderer
import p02_obj.WebGlRenderer as ObjectWebGlRenderer
import p03_cube.WebGlRenderer as CubeWebGlRenderer
import p04_texture_cube.WebGlRenderer as CubeTextureWebGlRenderer

fun main() {
    // HTML elements
    val canvas by lazy { document.getElementById("canvas") as HTMLElement }
    val controls by lazy { document.getElementById("object-controls") as HTMLElement }

    val triangleButton by lazy { document.getElementById("triangle") as HTMLButtonElement }
    val objectButton by lazy { document.getElementById("object") as HTMLButtonElement }
    val cubeButton by lazy { document.getElementById("cube") as HTMLButtonElement }
    val cubeTextureButton by lazy { document.getElementById("cube-texture") as HTMLButtonElement }

    // Renderers
    val triangleRenderer by lazy { TriangleWebGlRenderer() }
    val objectRenderer by lazy { ObjectWebGlRenderer() }
    val cubeRenderer by lazy { CubeWebGlRenderer() }
    val cubeTextureRenderer by lazy { CubeTextureWebGlRenderer() }

    document.body?.onload = {
        triangleButton.setupCanvasOnClick(
            triangleRenderer,
            onElementShow = { toggleElementsVisibility(arrayOf(canvas), hidden = false) })

        objectButton.setupCanvasOnClick(
            objectRenderer,
            onElementShow = { toggleElementsVisibility(arrayOf(canvas, controls), hidden = false) })

        cubeButton.setupCanvasOnClick(
            cubeRenderer,
            onElementShow = { toggleElementsVisibility(arrayOf(canvas), hidden = false) })

        cubeTextureButton.setupCanvasOnClick(
            cubeTextureRenderer,
            onElementShow = { toggleElementsVisibility(arrayOf(canvas), hidden = false) })

    }
}

private fun toggleElementsVisibility(array: Array<HTMLElement>, hidden: Boolean) {
    array.forEach { it.hidden = hidden }
}

