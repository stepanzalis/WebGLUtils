package base.ext

import exceptions.WebGLException

/**
 * Catching any instance of [WebGLException]
 * If exception is caught, browser alert dialog is shown.
 */
fun tryCatchWebGlException(function: () -> Unit) {
    try {
        function.invoke()
    } catch (e: WebGLException) {
        js("alert('Exception, check browser console for info.')")
    }
}