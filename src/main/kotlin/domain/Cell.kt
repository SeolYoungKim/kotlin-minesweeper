package domain

interface Cell {
    fun isLandMine(): Boolean
}

class LandMineCell : Cell {
    override fun isLandMine(): Boolean {
        return true
    }
}

class SafeCell : Cell {
    var landMineCountNearby: Int = 0

    override fun isLandMine(): Boolean {
        return false
    }
}
