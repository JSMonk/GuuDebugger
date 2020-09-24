package exceptions

import java.lang.Exception

data class Position(val filePath: String, val line: UInt, val column: UInt)

open class PositionedException(message: String, val position: Position) : Exception(message)