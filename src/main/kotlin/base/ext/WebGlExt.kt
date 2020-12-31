package base.ext

import org.khronos.webgl.*
import org.khronos.webgl.WebGLRenderingContext.Companion.LINEAR
import org.khronos.webgl.WebGLRenderingContext.Companion.RGBA
import org.khronos.webgl.WebGLRenderingContext.Companion.TEXTURE_2D
import org.khronos.webgl.WebGLRenderingContext.Companion.TEXTURE_MAG_FILTER
import org.khronos.webgl.WebGLRenderingContext.Companion.TEXTURE_MIN_FILTER
import org.khronos.webgl.WebGLRenderingContext.Companion.UNSIGNED_BYTE
import org.w3c.dom.Image

fun WebGLRenderingContext.clearColorBuffer(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) {
    clearColor(red, green, blue, alpha)
    clear(WebGLRenderingContext.COLOR_BUFFER_BIT)
}

fun WebGLRenderingContext.initViewport(width: Int, height: Int) = viewport(0, 0, width, height)

fun WebGLRenderingContext.initUniformLoc(program: WebGLProgram?, name: String) =
    program?.let { getUniformLocation(program, name) }

/**
 * Loading image texture to instance of [Image]
 * @param onTextureLoaded Callback when resource is loaded
 *
 * FIXME: Another way is to create 1px color texture and load it immediately without callback and then put it to texture
 */
fun WebGLRenderingContext.loadImageTexture(url: String, onTextureLoaded: (texture: WebGLTexture?) -> Unit): WebGLTexture? {
    val image = Image()
    val texture = createTexture()

    bindTexture(TEXTURE_2D, texture)

    image.onload = {
        bindTexture(TEXTURE_2D, texture)

        texParameteri(TEXTURE_2D, TEXTURE_MIN_FILTER, LINEAR)
        texParameteri(TEXTURE_2D, TEXTURE_MAG_FILTER, LINEAR)

        texImage2D(
            TEXTURE_2D, 0, RGBA,
            RGBA, UNSIGNED_BYTE, image
        )
        onTextureLoaded.invoke(texture)
    }

    image.src = url
    return texture
}
