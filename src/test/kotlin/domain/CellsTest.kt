package domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize

object NoneShufflingStrategy : ShufflingStrategy {
    override fun shuffle(cells: List<Cell>): List<Cell> {
        return cells
    }
}

class CellsTest : FreeSpec({
    "SafeCell 개수와 LandMineCell 개수를 입력 받아 List<Cell>를 가진 Cells 생성한다" {
        val safeCellsCount = 95
        val landMinesCount = 5

        val cells = Cells.create(
            safeCellsCount = safeCellsCount,
            landMinesCount = landMinesCount,
            shufflingStrategy = NoneShufflingStrategy
        )

        cells.elements shouldHaveSize safeCellsCount + landMinesCount
        cells.elements.filterIsInstance<SafeCell>() shouldHaveSize safeCellsCount
        cells.elements.filterIsInstance<LandMineCell>() shouldHaveSize landMinesCount
    }

    "SafeCell 개수가 0이하이거나 LandMineCell 개수가 0 이하인 경우 예외를 발생시킨다" - {
        listOf(
            1 to 0,
            0 to 1,
            0 to 0,
            -1 to 1,
            1 to -1,
            -1 to -1,
        ).forEach { pair ->
            val safeCellsCount = pair.first
            val landMinesCount = pair.second

            "입력값: safeCellsCount=${safeCellsCount}, landMinesCount=${landMinesCount}" {
                shouldThrow<IllegalArgumentException> {
                    Cells.create(
                        safeCellsCount = safeCellsCount,
                        landMinesCount = landMinesCount,
                        shufflingStrategy = NoneShufflingStrategy
                    )
                }
            }
        }
    }
})
