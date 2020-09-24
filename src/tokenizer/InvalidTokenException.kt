package tokenizer

import exceptions.Position
import exceptions.PositionedException

class InvalidTokenException(
    invalidToken: String,
    position: Position
) : PositionedException("Invalid token '$invalidToken'", position)
