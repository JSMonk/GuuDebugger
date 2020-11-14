package tokenizer

import exceptions.Position
import extended.String.unbrase
import tokenizer.Grammatic.isValidInt
import tokenizer.Grammatic.isValidIdentifier
import tokenizer.Grammatic.isValidString

sealed class Token(val position: Position) {
    companion object {
        fun from(word: String, position: Position): Token =
             when (word) {
                Keyword.SUB.value -> FunctionDeclarationToken(position)
                Keyword.SET.value -> SetStatementToken(position)
                Keyword.CALL.value -> CallStatementToken(position)
                Keyword.PRINT.value -> PrintStatementToken(position)
                else -> when {
                    word.isValidInt() -> NumberLiteralToken(word.toInt(), position)
                    word.isValidString() -> StringLiteralToken(word.unbrase(), position)
                    word.isValidIdentifier() -> IdentifierToken(word, position)
                    else -> throw InvalidTokenException(word, position)
                }
            }
    }

    class FunctionDeclarationToken(position: Position): Token(position)
    class EndFunctionDeclarationToken(position: Position): Token(position)
    class CallStatementToken(position: Position): Token(position)
    class SetStatementToken(position: Position): Token(position)
    class PrintStatementToken(position: Position): Token(position)
    class NumberLiteralToken(val value: Int, position: Position): Token(position)
    class StringLiteralToken(val value: String, position: Position): Token(position)
    class IdentifierToken(val value: String, position: Position): Token(position)
}

