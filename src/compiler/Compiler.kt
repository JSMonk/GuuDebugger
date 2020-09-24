package compiler

import parser.AbstractSyntaxTree

fun SymbolTable.compile(ast: AbstractSyntaxTree.Program) {
    ast.body.forEach {
        val name = it.identifier.name
        createFunction(name, it.compile(name), it.position)
    }
}

fun AbstractSyntaxTree.FunctionDeclaration.compile(name: String) =
    Entity.Function(name, body.flatMap { it.generateOpCode() })

fun AbstractSyntaxTree.Statement.generateOpCode(): List<OperationCode> =
    when (this) {
        is AbstractSyntaxTree.Statement.CallStatement -> listOf(
                OperationCode.OP_CALL(identifier.name, position),
                OperationCode.OP_END_CALL(position)
        )
        is AbstractSyntaxTree.Statement.SetStatement ->
            when (argument) {
                is AbstractSyntaxTree.Expression.PrimitiveValue -> listOf(
                        OperationCode.OP_PUSH_PRIMITIVE(argument.value, argument.position),
                        OperationCode.OP_SET(identifier.name, position)
                )
                is AbstractSyntaxTree.Expression.Identifier -> listOf(
                    OperationCode.OP_PUSH(argument.name, position),
                    OperationCode.OP_SET(variableName = identifier.name, position = position)
                )
            }
        is AbstractSyntaxTree.Statement.PrintStatement ->
            when (argument) {
                is AbstractSyntaxTree.Expression.PrimitiveValue -> listOf(
                        OperationCode.OP_PUSH_PRIMITIVE(argument.value, position),
                        OperationCode.OP_PRINT(position)
                )
                is AbstractSyntaxTree.Expression.Identifier -> listOf(
                    OperationCode.OP_PUSH(argument.name, position),
                    OperationCode.OP_PRINT(position)
                )
            }
    }