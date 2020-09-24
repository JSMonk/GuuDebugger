package compiler

import exceptions.Position

sealed class OperationCode(val position: Position) {
    class OP_PRINT(position: Position): OperationCode(position)
    class OP_END_CALL(position: Position): OperationCode(position)
    class OP_SET(val variableName: String, position: Position): OperationCode(position)
    class OP_CALL(val functionName: String, position: Position): OperationCode(position)
    class OP_PUSH(val variableName: String, position: Position): OperationCode(position)
    class OP_PUSH_PRIMITIVE(val value: Any, position: Position): OperationCode(position)
}