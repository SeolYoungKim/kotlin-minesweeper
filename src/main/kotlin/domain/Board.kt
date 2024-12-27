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
    }
}
