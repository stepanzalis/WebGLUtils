package base

import base.data.CanvasInfo
import base.data.MouseEventInfo
import kotlinx.browser.document
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent

/**
 * This interface provides mouse button events [EventListener.mousemove], [EventListener.mouseup], [EventListener.mousedown] functionality
 * To use this listener, instance of [BaseWebGlCanvas] must implement this interface and must override
 * [canvasSize], [mouseEvent] and [mouseListener]
 *
 * @property mouseEvent Storing info about last X, Y coordinate and when is dragging is used
 * @property canvasSize Provides info about canvas size
 * @property mouseListener Lambda which is invoked when mouse button is released
 */
interface MouseListener {

    val canvasSize: CanvasInfo
    val mouseEvent: MouseEventInfo
    var mouseListener: ((Double, Double) -> Unit)?

    fun initMouseListeners() {
        initMouseDownListener()
        initMouseMoveListener()
        initMouseUpListener()
    }

    /**
     * Dragging mouse button
     * [mouseListener] is invoked only when user actually dragged the cursor (mouse button is pressed and moving)
     */
    private fun initMouseMoveListener() = document.addEventListener(MouseDragged, EventListener {
        it as MouseEvent

        val x = it.clientX
        val y = it.clientY

        // Invoke [mouseListener] only when dragging was marked as true
        if (mouseEvent.dragging) {
            val dx = x - mouseEvent.oldX
            val dy = y - mouseEvent.oldY
            mouseListener?.invoke(dx, dy)
        }

        mouseEvent.oldX = x.toDouble()
        mouseEvent.oldY = y.toDouble()
    }, false)

    /**
     * Enable dragging flag and store info about cursor coordinates when mouse button is pressed
     * Drag is enabled only when it's inside the canvas
     */
    private fun initMouseDownListener() = document.addEventListener(MouseButtonPressed, EventListener {
        it as MouseEvent

        val x = it.clientX
        val y = it.clientY

        if (x in 0..BaseWebGlCanvas.DefaultWidth && y in 0..BaseWebGlCanvas.DefaultWidth) {
            mouseEvent.oldX = x.toDouble()
            mouseEvent.oldY = y.toDouble()
            mouseEvent.dragging = true
        }
    }, false)

    /**
     * Disables dragging flag when mouse button in released
     */
    private fun initMouseUpListener() = document.addEventListener(MouseButtonReleased, EventListener {
        mouseEvent.dragging = false
    }, false)

    companion object {
        private const val MouseDragged = "mousemove"
        private const val MouseButtonReleased = "mouseup"
        private const val MouseButtonPressed = "mousedown"
    }
}