package extended.PositionedException

import exceptions.PositionedException

val PositionedException.messageForCLI: String
    get() = "${position.filePath} (${position.line + 1u}:${position.column + 1u}): ${message}"