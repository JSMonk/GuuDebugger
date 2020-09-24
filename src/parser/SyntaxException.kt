package parser

import exceptions.Position
import exceptions.PositionedException

class SyntaxException(message: String, position: Position) : PositionedException(message, position)