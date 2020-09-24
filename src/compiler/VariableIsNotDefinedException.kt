package compiler

import exceptions.Position
import exceptions.PositionedException

class VariableIsNotDefinedException(
    variableName: String,
    position: Position
): PositionedException("Variable '$variableName' is not defined", position)
