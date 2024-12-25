package domain

class Board(height: Int, width: Int, countOfLandMines: Int) {
    val matrix: List<Cells>

    init {
        val totalCountOfCells = height * width
        val cells = Cells.createWithShuffling(
            countOfSafeCells = totalCountOfCells - countOfLandMines,
            countOfLandMineCells = countOfLandMines,
        )

        var fromIndex = 0
        var toIndex = width

        matrix = (1..height).map {
            val subCells = cells.subList(fromIndex, toIndex)
            fromIndex += width
            toIndex += width
            subCells
        }
    }
}
