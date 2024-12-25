package domain

class Cells(val elements: List<Cell>) {
    fun subList(fromIndex: Int, toIndex: Int): Cells {
        return Cells(elements.subList(fromIndex, toIndex))
    }

    companion object {
        fun createWithShuffling(countOfSafeCells: Int, countOfLandMineCells: Int): Cells {
            val safeCells = (1..countOfSafeCells).map { SafeCell() }
            val landMineCells = (1..countOfLandMineCells).map { LandMineCell() }

            val cells = safeCells + landMineCells
            return Cells(cells.shuffled())
        }
    }
}
