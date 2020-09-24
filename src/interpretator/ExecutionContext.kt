package interpretator

import compiler.SymbolTable

class ExecutionContext(val table: SymbolTable, val callStack: CallStack, val valueStack: ValueStack)