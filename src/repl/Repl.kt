package repl

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

open class Repl(private val filepath: String) {
    val io = Scanner(System.`in`)

    init {
        showFileContent()
        println()
        showCommands()
        println()
    }

    protected fun showFileContent() {
        val headerLength = 5
        println(".".repeat(headerLength))
        FileInputStream(filepath).use {
            BufferedReader(InputStreamReader(it)).use {
                var line = it.readLine()
                var lineNumber = 1
                while (line != null) {
                    print(lineNumber.toString().let {
                        it + " ".repeat(headerLength - it.length - 1) + "| "
                    })
                    println(line)
                    lineNumber++
                    line = it.readLine()
                }
            }
        }
        println("-".repeat(headerLength))
    }

    protected fun ReplCommand.toPresentableString() =
            name.toLowerCase().let { "* $it${"\t".repeat(if (it.length < 2) 2 else 1)}- $description" }

    protected open fun showCommands() {
        println("Available Commands:")
        enumValues<ReplCommands>().forEach {
            println(it.toPresentableString())
        }
    }

    protected fun executeCommand(command: String) {
        when (command) {
            ReplCommands.HELP.name -> showCommands()
            else -> println("Illegal command. Put 'help' to show all commands.")
        }
    }
}