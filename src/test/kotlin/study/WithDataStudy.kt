package study

import domain.RowCells
import domain.NoneShufflingStrategy
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData

class WithDataStudy: FunSpec({
    context("SafeCell 개수가 0이하이거나 LandMineCell 개수가 0 이하인 경우 예외를 발생시킨다") {  // Container Scope에 추가한다
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
})

class WithDataStudy2: FreeSpec({
    // 밖에다 정의해도 된다 (Root Scope)
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

    "FreeSpec의 -는 ContainerScope이다." - {
        // - 로 추가되는 컨텍스트는 Container Scope이다.
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

    // https://kotest.io/docs/framework/datatesting/data-driven-testing.html 여기를 보면, leaf 내에 정의할 수 없다고 한다. TerminalScope는 leaf이기 때문에 정의가 안되는 것이다.
    "FreeSpec의 경우 아래의 컨텍스트가 Root or Container 범위가 아니라서 정의가 안된다." {  // TerminalScope 에 추가된다 (여기에는 withData를 추가할 수 없다)
        // 아래의 주석을 풀면 에러가 발생하면서 테스트가 실패한다!
//        data class Count(val safeCellsCount: Int, val minesCount: Int)
//        withData(
//            Count(1, 0),
//            Count(0, 1),
//            Count(0, 0),
//            Count(-1, 1),
//            Count(1, -1),
//            Count(-1, -1),
//        ) { (safeCellsCount, minesCount) ->
//            shouldThrow<IllegalArgumentException> {
//                Cells.create(
//                    safeCellsCount = safeCellsCount,
//                    minesCount = minesCount,
//                    shufflingStrategy = NoneShufflingStrategy
//                )
//            }
//        }
    }
})

