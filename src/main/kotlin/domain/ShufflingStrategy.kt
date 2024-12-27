package domain

interface ShufflingStrategy {
    fun shuffle(cells: List<Cell>): List<Cell>
}

object DefaultShufflingStrategy : ShufflingStrategy {
    override fun shuffle(cells: List<Cell>): List<Cell> {
        return cells.shuffled()
    }
}
