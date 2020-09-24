package parser

import exceptions.Position
import extended.Iterator.nextOrNull
import tokenizer.Token

fun Token.toAbstractSyntaxTreeNode(iterator: Iterator<Token>): AbstractSyntaxTree =
    when (this) {
        is Token.IdentifierToken -> AbstractSyntaxTree.Expression.Identifier(value, position)
        is Token.NumberLiteralToken -> AbstractSyntaxTree.Expression.PrimitiveValue.NumberLiteral(value, position)
        is Token.StringLiteralToken -> AbstractSyntaxTree.Expression.PrimitiveValue.StringLiteral(value, position)
        is Token.CallStatementToken -> {
            when (val next = iterator.nextOrNull()?.toAbstractSyntaxTreeNode(iterator)) {
                is AbstractSyntaxTree.Expression.Identifier -> AbstractSyntaxTree.Statement.CallStatement(next, position)
                else -> throw SyntaxException("Expected identifier", position)
            }
        }
        is Token.PrintStatementToken -> {
            when (val next = iterator.nextOrNull()?.toAbstractSyntaxTreeNode(iterator)) {
                is AbstractSyntaxTree.Expression -> AbstractSyntaxTree.Statement.PrintStatement(next, position)
                else -> throw SyntaxException("Expected identifier or literal", position)
            }
        }
        is Token.SetStatementToken -> {
            when (val maybeIdentifier = iterator.nextOrNull()?.toAbstractSyntaxTreeNode(iterator)) {
                is AbstractSyntaxTree.Expression.Identifier -> {
                    when (val maybeExpression = iterator.nextOrNull()?.toAbstractSyntaxTreeNode(iterator)) {
                        is AbstractSyntaxTree.Expression -> AbstractSyntaxTree.Statement.SetStatement(maybeIdentifier, maybeExpression, position)
                        else -> throw SyntaxException("Expected identifier or literal", position)
                    }
                }
                else -> throw SyntaxException("Expected identifier", position)
            }
        }
        is Token.FunctionDeclarationToken -> {
           when (val maybeIdentifier = iterator.nextOrNull()?.toAbstractSyntaxTreeNode(iterator)) {
               is AbstractSyntaxTree.Expression.Identifier -> {
                   val functionBody = arrayListOf<AbstractSyntaxTree.Statement>()
                   while (iterator.hasNext()) {
                       val token = iterator.next()
                       if (token is Token.EndFunctionDeclarationToken) {
                           break
                       }
                       when (val statement = token.toAbstractSyntaxTreeNode(iterator)) {
                           is AbstractSyntaxTree.Statement -> functionBody.add(statement)
                           else -> throw SyntaxException("Function body can contains only statements", position)
                       }
                   }
                   AbstractSyntaxTree.FunctionDeclaration(maybeIdentifier, functionBody, position)
               }
               else -> throw SyntaxException("Expected identifier", position)
           }
        }

        is Token.EndFunctionDeclarationToken -> throw IllegalAccessException("EndFunctionDeclarationToken always should be skipped")
    }

fun Sequence<Token>.toAbstractSyntaxTree(filepath: String): AbstractSyntaxTree.Program {
    var iterator = this.iterator()
    val programBody = arrayListOf<AbstractSyntaxTree.FunctionDeclaration>()
    while (iterator.hasNext()) {
        when (val statement = iterator.next().toAbstractSyntaxTreeNode(iterator)) {
            is AbstractSyntaxTree.FunctionDeclaration -> programBody.add(statement)
            else -> throw SyntaxException(
                "File can contain only function declarations. All statements should be placed inside functions",
                statement.position
            )
        }
    }
    return AbstractSyntaxTree.Program(programBody, Position(filepath, 0u, 0u))
}