package domain

class Cells(val elements: List<Cell>) {
    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): Cells {
        return Cells(elements.subList(fromIndex, toIndex))
    }

    companion object {
        fun createWithShuffling(
            safeCellsCount: Int,
            landMinesCount: Int,
        ): Cells {
            val safeCells = (1..safeCellsCount).map { SafeCell() }
            val landMineCells = (1..landMinesCount).map { LandMineCell() }

            val cells = safeCells + landMineCells
            return Cells(cells.shuffled())
        }
    }
}
