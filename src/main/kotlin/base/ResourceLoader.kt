package base

import org.w3c.dom.events.EventListener
import org.w3c.xhr.XMLHttpRequest

class ResourceLoader {

    private data class ResourceInfo(
        var loaded: Boolean = false,
        var content: String? = null
    )

    private val resourceMap: MutableMap<String, ResourceInfo> = HashMap()

    fun loadResources(vararg resourceLocations: String, onLoadedResources: () -> Unit) {
        resourceLocations.forEach { location ->
            resourceMap[location] = ResourceInfo()
            loadResource(location) {
                resourceMap[location]?.loaded = true
                resourceMap[location]?.content = it
            }
            console.log("Loaded resource: $location")
        }
        onLoadedResources.invoke()
    }

    private fun loadResource(uri: String, loaded: (String) -> Unit) {
        val request = XMLHttpRequest()
        request.open("GET", uri, false)
        request.addEventListener("load", EventListener {
            loaded.invoke(request.responseText)
        })
        request.send()
    }

    operator fun get(name: String) = resourceMap[name]?.content
}