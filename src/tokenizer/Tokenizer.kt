package tokenizer

import exceptions.Position
import extended.FileInputStream.*
import extended.Char.isNewline
import tokenizer.Grammatic.isStringBorder
import java.io.FileInputStream
import java.lang.StringBuilder

fun FileInputStream.getTokensSequence(filepath: String) = sequence<Token> {
    var lastWord = StringBuilder()
    var hadYield = false
    var inStringCtx = false
    var lastPosition = Position(filepath, 0u, 0u)
    for (c in this@getTokensSequence) {
        if (c.isStringBorder()) {
            inStringCtx = !inStringCtx
        }
        if (c.isWhitespace() && !inStringCtx) {
           if (lastWord.isNotEmpty()) {
               yieldTokenFrom(lastWord, hadYield, lastPosition)
               if (!hadYield) {
                   hadYield = true
               }
               lastWord = StringBuilder()
           }
            if (c.isNewline()) {
                lastPosition = lastPosition.copy(line = lastPosition.line + 1u, column = 0u)
            }
        } else {
            lastWord.append(c)
        }
        lastPosition = lastPosition.copy(column = lastPosition.column + 1u)
    }
    if (lastWord.isNotEmpty()) {
        yieldTokenFrom(lastWord, hadYield, lastPosition)
    }
}

suspend inline fun SequenceScope<Token>.yieldTokenFrom(word: StringBuilder, hadYield: Boolean, position: Position) {
    val token = Token.from(word.toString(), position)
    if (token is Token.FunctionDeclarationToken && hadYield) {
        yield(Token.EndFunctionDeclarationToken(position))
    }
    yield(token)
}

