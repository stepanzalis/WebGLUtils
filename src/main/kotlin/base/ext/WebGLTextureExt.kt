package base.ext

import exceptions.TextureOutOfRangeException
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLRenderingContext.Companion.TEXTURE0
import org.khronos.webgl.WebGLRenderingContext.Companion.TEXTURE31
import org.khronos.webgl.WebGLRenderingContext.Companion.TEXTURE_2D
import org.khronos.webgl.WebGLTexture

fun WebGLTexture.useTexture(webGl: WebGLRenderingContext, textureIndex: Int = TEXTURE0) = with(webGl) {
    if (textureIndex < TEXTURE0 || textureIndex > TEXTURE31) throw TextureOutOfRangeException

    webGl.activeTexture(textureIndex)
    webGl.bindTexture(TEXTURE_2D, this@useTexture)
}