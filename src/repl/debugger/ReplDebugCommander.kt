package repl.debugger

import compiler.Entity
import compiler.SymbolTable
import debugger.DebugCommand
import debugger.DebugCommander
import extended.String.toSafeUpperCase
import interpretator.CallStack
import repl.IllegalDebuggerCommand
import repl.Repl
import repl.ReplCommands
import repl.ReplDebuggerCommands

class ReplDebugCommander(filepath: String) : Repl(filepath), DebugCommander {
    override fun showStackTrace(callStack: CallStack) {
        println("| CallStack: ")
        for (function in callStack.reversed()) {
            println("| -> sub \"${function.name}\"")
        }
    }

    override fun showVariables(table: SymbolTable) {
        println("| Variables: ")
        for ((name, entity) in table) {
            if (entity is Entity.Variable) {
                println("| \"$name\" = ${entity.value}")
            }
        }
    }

    override fun showCommands() {
        super.showCommands()
        enumValues<ReplDebuggerCommands>().forEach {
            println(it.toPresentableString())
        }
    }

    override suspend fun getDebugCommand(position: exceptions.Position): DebugCommand {
        while (true) {
            print("Now we at line \"${position.line + 1u}\", enter cmd: ")
            when (val command = io.nextLine().toSafeUpperCase()) {
                ReplDebuggerCommands.I.name -> return DebugCommand.STEP_INTO
                ReplDebuggerCommands.O.name -> return DebugCommand.STEP_OVER
                ReplDebuggerCommands.TRACE.name -> return DebugCommand.SHOW_STACK_TRACE
                ReplDebuggerCommands.VAR.name -> return DebugCommand.SHOW_VARIABLES
                ReplCommands.X.name -> return DebugCommand.BREAK
                else -> super.executeCommand(command)
            }
        }
        throw IllegalDebuggerCommand()
    }
}