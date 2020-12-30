package base

import kotlinx.browser.document
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.KeyboardEvent

/**
 * This interface provides keyboard button events [EventListener.keyup]
 * To use this listener, instance of [BaseWebGlCanvas] must implement this interface and must override
 * [eventListener],
 *
 * @property eventListener General event listener
 *
 * To get info which button is pressed, you must check if passed event is instance of [KeyboardEvent], then you can get
 * actually pressed button key from [KeyboardEvent.key] property
 */
interface KeyboardListener {

    val eventListener: EventListener
    fun initKeyboardListeners() = document.addEventListener(KeyReleased, eventListener, false)

    companion object {
        private const val KeyReleased = "keyup"
    }
}