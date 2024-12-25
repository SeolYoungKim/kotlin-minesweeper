package domain

class Cells(val elements: List<Cell>) {
    companion object {
        fun createWithShuffling(countOfSafeCells: Int, countOfLandMineCells: Int): Cells {
            val safeCells = (1..countOfSafeCells).map { SafeCell() }
            val landMineCells = (1..countOfLandMineCells).map { LandMineCell() }

            val cells = safeCells + landMineCells
            return Cells(cells.shuffled())
        }
    }
}
