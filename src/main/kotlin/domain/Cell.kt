package domain

interface Cell {
    fun isMine(): Boolean
}

class Mine : Cell {
    override fun isMine(): Boolean {
        return true
    }
}

class SafeCell : Cell {
    var mineCountNearby: Int = 0

    override fun isMine(): Boolean {
        return false
    }
}
