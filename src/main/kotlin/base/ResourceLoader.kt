package base

import org.w3c.dom.events.EventListener
import org.w3c.xhr.XMLHttpRequest

class ResourceLoader {

    private data class ResourceInfo(
        var loaded: Boolean = false,
        var code: String? = null
    )

    private val resourceMap: MutableMap<String, ResourceInfo> = HashMap()

    fun loadResources(vararg resourceLocations: String, onLoadedResources: () -> Unit) {
        resourceLocations.forEach { location ->
            resourceMap[location] = ResourceInfo()
            loadProgram(location) {
                resourceMap[location]?.loaded = true
                resourceMap[location]?.code = it
            }
            console.log("Loaded resource: $location")
        }
        onLoadedResources.invoke()
    }

    private fun loadProgram(uri: String, loaded: (String) -> Unit) {
        val request = XMLHttpRequest()
        request.open("GET", uri, false)
        request.addEventListener("load", EventListener {
            loaded.invoke(request.responseText)
        })
        request.send()
    }

    operator fun get(name: String) = resourceMap[name]?.code
}