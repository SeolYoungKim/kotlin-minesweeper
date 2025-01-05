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

class RowCellsTest : FreeSpec({
    "SafeCell 개수와 LandMineCell 개수를 입력 받아 List<Cell>를 가진 Cells 생성한다" {
        val safeCellsCount = 95
        val minesCount = 5

        val rowCells = RowCells.create(
            safeCellsCount = safeCellsCount,
            minesCount = minesCount,
            shufflingStrategy = NoneShufflingStrategy
        )

        rowCells.elements shouldHaveSize safeCellsCount + minesCount
        rowCells.elements.filterIsInstance<SafeCell>() shouldHaveSize safeCellsCount
        rowCells.elements.filterIsInstance<Mine>() shouldHaveSize minesCount
    }

    "셔플링 전략에 따라 cells를 섞을 수도 있다" {
        val safeCellsCount = 95
        val minesCount = 5

        val noneShufflingRowCells = RowCells.create(
            safeCellsCount = safeCellsCount,
            minesCount = minesCount,
            shufflingStrategy = NoneShufflingStrategy
        )

        val shufflingRowCells = RowCells.create(
            safeCellsCount = safeCellsCount,
            minesCount = minesCount,
            shufflingStrategy = FixedSeedShufflingStrategy
        )

        val noneShufflingElements = noneShufflingRowCells.elements
        val shufflingElements = shufflingRowCells.elements

        noneShufflingElements.size shouldBe shufflingElements.size
        noneShufflingElements.map { it.javaClass } shouldNotBeEqual shufflingElements.map { it.javaClass }
    }

    "SafeCell 개수가 0이하이거나 mineCell 개수가 0 이하인 경우 예외를 발생시킨다" - {
        data class Count(val safeCellsCount: Int, val minesCount: Int)
        withData(
            Count(1, 0),
            Count(0, 1),
            Count(0, 0),
            Count(-1, 1),
            Count(1, -1),
            Count(-1, -1),
        ) { (safeCellsCount, minesCount) ->
            shouldThrow<IllegalArgumentException> {
                RowCells.create(
                    safeCellsCount = safeCellsCount,
                    minesCount = minesCount,
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
            val previousRowCells = RowCells(listOf(SafeCell(), Mine(), SafeCell()))
            val targetRowCells = RowCells(listOf(Mine(), SafeCell(), Mine()))
            val nextRowCells = RowCells(listOf(SafeCell(), Mine(), SafeCell()))

            targetRowCells.fillMineCountNearBySafeCell(
                previousRowCells = previousRowCells,
                nextRowCells = nextRowCells
            )

            val safeCell = targetRowCells[1] as SafeCell
            safeCell.mineCountNearby shouldBe 4
        }

        /**
         * 테스트 데이터 예시
         *    C * C  <-- targetCells
         *    * C *
         */
        "previousCells가 존재하지 않고 nextCells가 존재하는 경우" {
            val targetRowCells = RowCells(listOf(SafeCell(), Mine(), SafeCell()))
            val nextRowCells = RowCells(listOf(Mine(), SafeCell(), Mine()))

            targetRowCells.fillMineCountNearBySafeCell(
                previousRowCells = null,
                nextRowCells = nextRowCells
            )

            val firstSafeCell = targetRowCells[0] as SafeCell
            val secondSafeCell = targetRowCells[2] as SafeCell

            firstSafeCell.mineCountNearby shouldBe 2
            secondSafeCell.mineCountNearby shouldBe 2
        }

        /**
         * 테스트 데이터 예시
         *    C * C
         *    * C *  <-- targetCells
         */
        "previousCells가 존재하고 nextCells가 존재하지 않는 경우" {
            val previousRowCells = RowCells(listOf(SafeCell(), Mine(), SafeCell()))
            val targetRowCells = RowCells(listOf(Mine(), SafeCell(), Mine()))

            targetRowCells.fillMineCountNearBySafeCell(
                previousRowCells = previousRowCells,
                nextRowCells = null
            )

            val safeCell = targetRowCells[1] as SafeCell
            safeCell.mineCountNearby shouldBe 3
        }
    }
})
