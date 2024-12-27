package domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe
import kotlin.random.Random

object NoneShufflingStrategy : ShufflingStrategy {
    override fun shuffle(cells: List<Cell>): List<Cell> {
        return cells
    }
}

object FixedSeedShufflingStrategy : ShufflingStrategy {
    override fun shuffle(cells: List<Cell>): List<Cell> {
        return cells.shuffled(Random(10))
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

    "셔플링 전략에 따라 cells를 섞을 수도 있다" {
        val safeCellsCount = 95
        val landMinesCount = 5

        val noneShufflingCells = Cells.create(
            safeCellsCount = safeCellsCount,
            landMinesCount = landMinesCount,
            shufflingStrategy = NoneShufflingStrategy
        )

        val shufflingCells = Cells.create(
            safeCellsCount = safeCellsCount,
            landMinesCount = landMinesCount,
            shufflingStrategy = FixedSeedShufflingStrategy
        )

        val noneShufflingElements = noneShufflingCells.elements
        val shufflingElements = shufflingCells.elements

        noneShufflingElements.size shouldBe shufflingElements.size
        noneShufflingElements.map { it.javaClass } shouldNotBeEqual shufflingElements.map { it.javaClass }
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
