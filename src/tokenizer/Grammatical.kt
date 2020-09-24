package tokenizer

enum class Keyword {
    SET,
    SUB,
    CALL,
    PRINT
}

object Grammatic {
    private val IDENTIFIER = Regex("""^[${'$'}_a-zA-Z]\w?[\w_${'$'}]*$""")
    private val NUMBER = Regex("""^-?\d+$""")
    private val STRING = Regex("""^".*?"$""")

    fun String.isValidInt() = NUMBER.matches(this)
    fun String.isValidString() = STRING.matches(this)
    fun String.isValidIdentifier() = IDENTIFIER.matches(this)
    fun Char.isStringBorder() = equals('"')
}
