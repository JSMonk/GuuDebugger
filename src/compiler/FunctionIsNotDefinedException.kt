package compiler

import exceptions.Position
import exceptions.PositionedException

class FunctionIsNotDefinedException(
    functionName: String,
    position: Position
): PositionedException("Function '$functionName' is not defined", position)
