package base.data

data class MouseEventInfo(
    var dragging: Boolean = false,
    var dX: Double = .0,
    var dY: Double = .0,
    var oldX: Double = .0,
    var oldY: Double = .0,
)
