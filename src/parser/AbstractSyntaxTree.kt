package parser

import exceptions.Position

sealed class AbstractSyntaxTree(val position: Position) {
    class Program(val body: List<FunctionDeclaration>, position: Position) : AbstractSyntaxTree(position)

    sealed class Expression(position: Position): AbstractSyntaxTree(position) {
        class Identifier(val name: String, position: Position): Expression(position)
        sealed class PrimitiveValue(val value: Any, position: Position): Expression(position) {
            class NumberLiteral(value: Int, position: Position): PrimitiveValue(value, position)
            class StringLiteral(value: String, position: Position): PrimitiveValue(value, position)
        }
    }

    sealed class Statement(position: Position): AbstractSyntaxTree(position) {
        class PrintStatement(
            val argument: Expression,
            position: Position
        ): Statement(position)

        class CallStatement(
            val identifier: Expression.Identifier,
            position: Position
        ): Statement(position)

        class SetStatement(
            val identifier: Expression.Identifier,
            val argument: Expression,
            position: Position
        ): Statement(position)
    }

    class FunctionDeclaration(
        val identifier: Expression.Identifier,
        val body: List<Statement>,
        position: Position
    ): AbstractSyntaxTree(position)
}

