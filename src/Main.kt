import compiler.SymbolTable
import compiler.compile
import exceptions.PositionedException
import extended.PositionedException.messageForCLI
import java.io.FileInputStream
import extended.String.toAbsolutePath
import debugger.Debugger
import repl.debugger.ReplDebugCommander
import interpretator.runProgram
import parser.toAbstractSyntaxTree
import tokenizer.getTokensSequence

suspend fun main(args: Array<String>) {
   if (args.isEmpty()) {
      return
   }
   val filepath = args.last().toAbsolutePath()
   FileInputStream(filepath).use {
      try {
         val tokens = it.getTokensSequence(filepath)
         val ast = tokens.toAbstractSyntaxTree(filepath)
         val table = SymbolTable()
         table.compile(ast)
         val executor = Debugger(table, ReplDebugCommander(filepath))
         executor.execute(runProgram(filepath))
      } catch (e: PositionedException) {
         System.err.println(e.messageForCLI)
      }
   }
}