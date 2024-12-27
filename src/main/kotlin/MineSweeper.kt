import domain.Board
import ui.MineSweeperPrinter
import ui.MineSweeperReader

fun main() {
    val height = MineSweeperReader.readHeight()
    val width = MineSweeperReader.readWidth()
    val countOfLandMines = MineSweeperReader.readCountOfLandMine()

    val board = Board(height = height, width = width, landMinesCount = countOfLandMines)

    MineSweeperPrinter.printGameStartMessage()
    MineSweeperPrinter.printBoard(board)
}
