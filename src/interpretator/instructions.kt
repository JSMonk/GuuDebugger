package interpretator

import compiler.OperationCode
import exceptions.Position
import interpretator.opcodes.Executor

const val MAIN_FUNCTION_NAME = "main"

fun runProgram(filename: String) =
    listOf(
            OperationCode.OP_CALL(MAIN_FUNCTION_NAME, Position(filename, 0u,0u))
    ).map(::Executor)