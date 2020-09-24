package interpretator

import compiler.SymbolTable
import interpretator.opcodes.Executor

class SimpleInterpretator(table: SymbolTable): Interpretator(table) {
    override suspend fun execute(operations: Collection<Executor>) {
        val executors = ArrayList(operations)
        var index = 0
        while (index < executors.size) {
            val executor = executors[index]
            val newExecutors = executor.execute(executionContext)
            if (newExecutors != null) {
                executors.addAll(index + 1, newExecutors)
            }
            index++
        }
    }
}
