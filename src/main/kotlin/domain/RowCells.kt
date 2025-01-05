package domain

class RowCells(val elements: List<Cell>) {
    /**
     * [fromIndex] : 포함
     * [toIndex] : 제외
     */
    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): RowCells {
        return RowCells(elements.subList(fromIndex, toIndex))
    }

    operator fun get(index: Int): Cell {
        return elements[index]
    }

    fun fillMineCountNearBySafeCell(previousRowCells: RowCells?, nextRowCells: RowCells?) {
        elements.forEachIndexed { index, cell ->
            if (cell is SafeCell) {
                val previousCount = countLandMineCells(previousRowCells, index)
                val midCount = countLandMineCells(this, index)
                val nextCount = countLandMineCells(nextRowCells, index)

                cell.mineCountNearby = previousCount + midCount + nextCount
            }
        }
    }

    private fun countLandMineCells(otherRowCells: RowCells?, currentCellIndex: Int): Int {
        return if (otherRowCells == null) {
            0
        } else {
            val fromIndex = getFromIndex(currentCellIndex)
            val toIndex = getToIndex(currentCellIndex)

            otherRowCells.elements
                .slice(fromIndex..toIndex)
                .count { cell -> cell.isMine() }
        }
    }

    private fun getFromIndex(currentCellIndex: Int): Int {
        return if (currentCellIndex == 0) {
            0
        } else {
            currentCellIndex - 1
        }
    }

    private fun getToIndex(currentCellIndex: Int): Int {
        return if (currentCellIndex == elements.lastIndex) {
            currentCellIndex
        } else {
            currentCellIndex + 1
        }
    }

    companion object {
        fun create(
            safeCellsCount: Int,
            minesCount: Int,
            shufflingStrategy: ShufflingStrategy
        ): RowCells {
            require(safeCellsCount > 0 && minesCount > 0) { "safeCellsCount와 minesCount는 0보다 커야 합니다." }

            val safeCells = (1..safeCellsCount).map { SafeCell() }
            val mines = (1..minesCount).map { Mine() }

            val cells = safeCells + mines
            return RowCells(shufflingStrategy.shuffle(cells))
        }
    }
}
