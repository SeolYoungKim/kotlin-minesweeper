package domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize

class CellsTest : FreeSpec({
    "SafeCell 개수와 LandMineCell 개수를 입력 받아 List<Cell>를 가진 Cells 생성한다" {
        val countOfSafeCells = 95
        val countOfLandMineCells = 5

        val cells = Cells.createWithShuffling(countOfSafeCells = countOfSafeCells, countOfLandMineCells = countOfLandMineCells)

        cells.elements shouldHaveSize countOfSafeCells + countOfLandMineCells
        cells.elements.filterIsInstance<SafeCell>() shouldHaveSize countOfSafeCells
        cells.elements.filterIsInstance<LandMineCell>() shouldHaveSize countOfLandMineCells
    }
})
