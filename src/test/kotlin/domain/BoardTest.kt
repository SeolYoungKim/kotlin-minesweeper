package domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class BoardTest : FreeSpec({
    "높이와 너비, 지뢰 개수를 입력받아 2차원 리스트를 가지는 게임판을 만든다" {
        val height = 10
        val width = 5
        val countOfLandMineCells = 5

        val board = Board(height = height, width = width, minesCount = countOfLandMineCells)

        board.matrix shouldHaveSize height
        board.matrix.forEach { cells -> cells.elements shouldHaveSize width }

        val mines =
            board.matrix.flatMap { cells ->
                cells.elements.filterIsInstance<Mine>()
            }
        mines.size shouldBe countOfLandMineCells
    }
})
