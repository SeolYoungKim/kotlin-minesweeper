package domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
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
        data class Count(val safeCellsCount: Int, val landMinesCount: Int)
        withData(
            Count(1, 0),
            Count(0, 1),
            Count(0, 0),
            Count(-1, 1),
            Count(1, -1),
            Count(-1, -1),
        ) { (safeCellsCount, landMinesCount) ->
            shouldThrow<IllegalArgumentException> {
                Cells.create(
                    safeCellsCount = safeCellsCount,
                    landMinesCount = landMinesCount,
                    shufflingStrategy = NoneShufflingStrategy
                )
            }
        }
    }

    "previousCells와 nextCells를 받아 targetCells의 근처 지뢰 수를 계산해서 채워준다" - {

        /**
         * 테스트 데이터 예시
         *    C * C
         *    * C *  <-- targetCells
         *    C * C
         */
        "previousCells, nextCells가 존재하는 경우" {
            val previousCells = Cells(listOf(SafeCell(), LandMineCell(), SafeCell()))
            val targetCells = Cells(listOf(LandMineCell(), SafeCell(), LandMineCell()))
            val nextCells = Cells(listOf(SafeCell(), LandMineCell(), SafeCell()))

            targetCells.fillLandMineCellCountNearBySafeCell(
                previousCells = previousCells,
                nextCells = nextCells
            )

            val safeCell = targetCells[1] as SafeCell
            safeCell.landMineCountNearby shouldBe 4
        }

        /**
         * 테스트 데이터 예시
         *    C * C  <-- targetCells
         *    * C *
         */
        "previousCells가 존재하지 않고 nextCells가 존재하는 경우" {
            val targetCells = Cells(listOf(SafeCell(), LandMineCell(), SafeCell()))
            val nextCells = Cells(listOf(LandMineCell(), SafeCell(), LandMineCell()))

            targetCells.fillLandMineCellCountNearBySafeCell(
                previousCells = null,
                nextCells = nextCells
            )

            val firstSafeCell = targetCells[0] as SafeCell
            val secondSafeCell = targetCells[2] as SafeCell

            firstSafeCell.landMineCountNearby shouldBe 2
            secondSafeCell.landMineCountNearby shouldBe 2
        }

        /**
         * 테스트 데이터 예시
         *    C * C
         *    * C *  <-- targetCells
         */
        "previousCells가 존재하고 nextCells가 존재하지 않는 경우" {
            val previousCells = Cells(listOf(SafeCell(), LandMineCell(), SafeCell()))
            val targetCells = Cells(listOf(LandMineCell(), SafeCell(), LandMineCell()))

            targetCells.fillLandMineCellCountNearBySafeCell(
                previousCells = previousCells,
                nextCells = null
            )

            val safeCell = targetCells[1] as SafeCell
            safeCell.landMineCountNearby shouldBe 3
        }
    }
})
