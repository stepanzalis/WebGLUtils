package base.data

enum class ShaderType(val suffix: String) {

    Vertex(".vert"),
    Fragment(".frag"),
    General(".glsl");

    companion object {
        fun isShader(name: String): Boolean {
            return (name.contains(Vertex.suffix) || name.contains(Fragment.suffix) || name.contains(General.suffix))
        }
    }
} 