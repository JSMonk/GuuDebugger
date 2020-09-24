package compiler

import exceptions.Position
import exceptions.PositionedException

class IllegalAssignException(
        message: String,
        position: Position
): PositionedException(message, position)
