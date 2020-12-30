package base

import org.w3c.dom.events.EventListener
import org.w3c.xhr.XMLHttpRequest

/**
 * Loading resources from folder, which must be marked as Resource root, otherwise resources are not recognized
 * Resources are stored in map, where key is name with file suffix
 *
 * That actual file is stored in [ResourceInfo] object
 * @property [ResourceInfo.content] is where the code for shaders, obj files are stored.
 *
 * Important note: This approach is far from perfect, but it works for now
 */
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