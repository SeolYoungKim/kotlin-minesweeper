package domain

class Cells(val elements: List<Cell>) {
    /**
     * [fromIndex] : 포함
     * [toIndex] : 제외
     */
    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): Cells {
        return Cells(elements.subList(fromIndex, toIndex))
    }

    operator fun get(index: Int): Cell {
        return elements[index]
    }

    fun fillLandMineCellCountNearBySafeCell(previousCells: Cells?, nextCells: Cells?) {
        elements.forEachIndexed { index, cell ->
            if (cell is SafeCell) {
                val previousCount = countLandMineCells(previousCells, index)
                val midCount = countLandMineCells(this, index)
                val nextCount = countLandMineCells(nextCells, index)

                cell.landMineCountNearby = previousCount + midCount + nextCount
            }
        }
    }

    private fun countLandMineCells(otherCells: Cells?, currentCellIndex: Int): Int {
        return if (otherCells == null) {
            0
        } else {
            val fromIndex = getFromIndex(currentCellIndex)
            val toIndex = getToIndex(currentCellIndex)

            otherCells.elements
                .slice(fromIndex..toIndex)
                .count { cell -> cell.isLandMine() }
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
