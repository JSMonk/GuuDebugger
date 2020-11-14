package repl

interface ReplCommand : Describable {
    val value: String
}

enum class ReplCommands(
        override val value: String,
        override val description: String
): ReplCommand {
    X("x", "exit"),
    HELP("help","show all available commands"),
}

enum class ReplDebuggerCommands(
        override val value: String,
        override val description: String
): ReplCommand {
    I("i", "step into"),
    O("o", "step over"),
    VAR("var", "show variables"),
    TRACE("trace","show stack trace")
}
