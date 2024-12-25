package ui

object ConsoleReader {
    private val numberRegex = Regex("^\\d+$")

    fun readNumber(): Int {
        val readLine = readLine()
        require(readLine.isNotBlank()) { "Blank를 입력할 수 없습니다." }
        require(readLine.matches(numberRegex)) { "숫자만 입력할 수 있습니다." }
        return readLine.toInt()
    }

    private fun readLine(): String {
        return readlnOrNull()
            ?: throw IllegalArgumentException("null을 입력할 수 없습니다.")
    }
}
