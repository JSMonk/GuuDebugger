package interpretator

import exceptions.Position
import exceptions.PositionedException

class StackOverflowException(position: Position) : PositionedException("Stack overflow", position)