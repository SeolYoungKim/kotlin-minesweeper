package ui

object MineSweeperReader {
    fun readHeight(): Int {
        MineSweeperPrinter.printInputHeightMessage()
        return ConsoleReader.readNumber()
    }

    fun readWidth(): Int {
        MineSweeperPrinter.printInputWidthMessage()
        return ConsoleReader.readNumber()
    }

    fun readCountOfLandMine(): Int {
        MineSweeperPrinter.printInputCountOfLandMineMessage()
        return ConsoleReader.readNumber()
    }
}
