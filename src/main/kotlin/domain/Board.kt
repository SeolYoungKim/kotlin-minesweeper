package domain

class Board(height: Int, width: Int, landMinesCount: Int) {
    val matrix: List<Cells>

    init {
        val totalCountOfCells = height * width
        val cells =
            Cells.create(
                safeCellsCount = totalCountOfCells - landMinesCount,
                landMinesCount = landMinesCount,
                shufflingStrategy = DefaultShufflingStrategy
            )

        matrix =
            (1..height).map { number ->
                val fromIndex = (number - 1) * width
                val toIndex = number * width
                cells.subList(fromIndex, toIndex)
            }

        countLandMineCellNearBySafeCell()
    }

    private fun countLandMineCellNearBySafeCell() {
        matrix.forEachIndexed { index, cells ->
            cells.fillLandMineCellCountNearBySafeCell(
                previousCells = getPreviousCellsOf(index),
                nextCells = getNextCellsOf(index),
            )
        }
    }

    private fun getPreviousCellsOf(index: Int): Cells? {
        return if (index == 0) {
            null
        } else {
            matrix[index - 1]
        }
    }

    private fun getNextCellsOf(index: Int): Cells? {
        return if (index == matrix.lastIndex) {
            null
        } else {
            matrix[index + 1]
        }
    }
}
