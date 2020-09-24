package debugger

import compiler.SymbolTable
import debugger.DebugCommander
import interpretator.Interpretator
import interpretator.SimpleInterpretator
import interpretator.opcodes.Executor

class Debugger(table: SymbolTable, val commander: DebugCommander): Interpretator(table) {
   val interpretator = SimpleInterpretator(table)

   override suspend fun execute(operations: Collection<Executor>) {
       val executors = ArrayList(operations)
       var index = 0
       execution@ while (index < executors.size) {
           val executor = executors[index]
           if (!executor.isSkipable) {
               when (val command = commander.getDebugCommand(executor.opcode.position)) {
                   DebugCommand.BREAK -> return
                   DebugCommand.SHOW_VARIABLES -> {
                       commander.showVariables(executionContext.table)
                       continue@execution
                   }
                   DebugCommand.SHOW_STACK_TRACE -> {
                       commander.showStackTrace(executionContext.callStack)
                       continue@execution
                   }
                   DebugCommand.STEP_INTO, DebugCommand.STEP_OVER -> {
                       val newExecutors = executor.execute(executionContext)
                       if (newExecutors != null) {
                           if (command == DebugCommand.STEP_OVER) {
                               interpretator.execute(newExecutors)
                           } else {
                               executors.addAll(index + 1, newExecutors)
                           }
                       }
                       index++
                   }
               }
           } else {
               executor.execute(executionContext)
               index++
           }
       }
    }
}
