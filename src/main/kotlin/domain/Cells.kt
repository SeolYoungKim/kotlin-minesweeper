package domain

class Cells(val elements: List<Cell>) {
    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): Cells {
        return Cells(elements.subList(fromIndex, toIndex))
    }

    companion object {
        fun create(
            safeCellsCount: Int,
            landMinesCount: Int,
            shufflingStrategy: ShufflingStrategy
        ): Cells {
            require(safeCellsCount > 0 && landMinesCount > 0) { "safeCellsCount와 landMinesCount는 0보다 커야 합니다." }

            val safeCells = (1..safeCellsCount).map { SafeCell() }
            val landMineCells = (1..landMinesCount).map { LandMineCell() }

            val cells = safeCells + landMineCells
            return Cells(shufflingStrategy.shuffle(cells))
        }
    }
}
