package interpretator

import compiler.SymbolTable
import exceptions.Position

interface Executable {
   fun execute(table: SymbolTable, callStack: CallStack, valueStack: ValueStack): Sequence<Position>
}