package ui

import domain.Board
import domain.Cell
import domain.RowCells
import domain.Mine
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

    private fun createCellsMessage(rowCells: RowCells): String {
        return rowCells.elements.joinToString(separator = " ") { cell ->
            createCellMessage(cell)
        }
    }

    private fun createCellMessage(cell: Cell): String {
        return when (cell) {
            is Mine -> "*"
            is SafeCell -> cell.mineCountNearby.toString()
            else -> throw IllegalStateException()
        }
    }
}
