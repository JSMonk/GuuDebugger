package debugger

import compiler.SymbolTable
import exceptions.Position
import interpretator.CallStack

enum class DebugCommand {
   STEP_OVER,
   STEP_INTO,
   SHOW_STACK_TRACE,
   SHOW_VARIABLES,
   BREAK
}

interface DebugCommander {
   suspend fun getDebugCommand(position: Position): DebugCommand
   fun showStackTrace(callStack: CallStack): Unit
   fun showVariables(table: SymbolTable): Unit
}