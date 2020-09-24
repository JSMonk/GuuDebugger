package repl

interface ReplCommand : Describable {
    val name: String
}

enum class ReplCommands(override val description: String): ReplCommand {
    X("exit"),
    HELP("show all available commands"),
}

enum class ReplDebuggerCommands(override val description: String): ReplCommand {
    I("step into"),
    O("step over"),
    VAR("show variables"),
    TRACE("show stack trace")
}
