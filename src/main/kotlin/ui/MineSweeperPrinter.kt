package ui

import domain.Board
import domain.Cell
import domain.Cells
import domain.LandMineCell
import domain.SafeCell

object MineSweeperPrinter {
    fun printInputHeightMessage() {
        println("높이를 입력하세요.")
    }

    fun printInputWidthMessage() {
        println("너비를 입력하세요.")
    }

    fun printInputCountOfLandMineMessage() {
        println("지뢰는 몇 개인가요?")
    }

    fun printGameStartMessage() {
        println("지뢰찾기 게임 시작")
    }

    fun printBoard(board: Board) {
        board.matrix.forEach { cells ->
            println(createCellsMessage(cells))
        }
    }

    private fun createCellsMessage(cells: Cells): String {
        return cells.elements.joinToString(separator = " ") { cell ->
            createCellMessage(cell)
        }
    }

    private fun createCellMessage(cell: Cell): String {
        return when (cell) {
            is LandMineCell -> "*"
            is SafeCell -> "C"
            else -> throw IllegalStateException()
        }
    }
}
