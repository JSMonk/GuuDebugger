package interpretator

import compiler.SymbolTable
import interpretator.opcodes.Executor

abstract class Interpretator(private val table: SymbolTable) {
   protected val executionContext = ExecutionContext(table, CallStack(), ValueStack())

   abstract suspend fun execute(operations: Collection<Executor>): Unit
}