package domain

class Board(height: Int, width: Int, minesCount: Int) {
    val matrix: List<RowCells>

    init {
        val totalCountOfCells = height * width
        val rowCells =
            RowCells.create(
                safeCellsCount = totalCountOfCells - minesCount,
                minesCount = minesCount,
                shufflingStrategy = DefaultShufflingStrategy
            )

        matrix =
            (1..height).map { number ->
                val fromIndex = (number - 1) * width
                val toIndex = number * width
                rowCells.subList(fromIndex, toIndex)
            }

        countMineNearBySafeCell()
    }

    private fun countMineNearBySafeCell() {
        matrix.forEachIndexed { index, rowCells ->
            rowCells.fillMineCountNearBySafeCell(
                previousRowCells = getPreviousCellsOf(index),
                nextRowCells = getNextCellsOf(index),
            )
        }
    }

    private fun getPreviousCellsOf(index: Int): RowCells? {
        return if (index == 0) {
            null
        } else {
            matrix[index - 1]
        }
    }

    private fun getNextCellsOf(index: Int): RowCells? {
        return if (index == matrix.lastIndex) {
            null
        } else {
            matrix[index + 1]
        }
    }
}
