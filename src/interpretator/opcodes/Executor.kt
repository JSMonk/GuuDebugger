package interpretator.opcodes

import compiler.OperationCode
import interpretator.ExecutionContext

class Executor(val opcode: OperationCode) {
    val isSkipable: Boolean
        get() =
            opcode is OperationCode.OP_PUSH_PRIMITIVE ||
            opcode is OperationCode.OP_PUSH ||
            opcode is OperationCode.OP_END_CALL

    fun execute(ctx: ExecutionContext): List<Executor>? =
        when (opcode) {
            is OperationCode.OP_PUSH_PRIMITIVE -> ctx.valueStack.push(opcode.value).let { null }
            is OperationCode.OP_END_CALL -> ctx.callStack.pop().let { null }
            is OperationCode.OP_PRINT -> print(ctx.valueStack.pop()).let { null }
            is OperationCode.OP_CALL -> ctx.table.getFunctionWithName(opcode.functionName, opcode.position)
                    .let { ctx.callStack.push(it, opcode.position); it.body.map(::Executor) }
            is OperationCode.OP_SET -> ctx.valueStack.pop()
                        .let { ctx.table.setOrCreateVariableWithName(opcode.variableName, it, opcode.position); null }
            is OperationCode.OP_PUSH -> ctx.table.getVariableWithName(opcode.variableName, opcode.position)
                        .let { ctx.valueStack.push(it.value); null }
        }
}
