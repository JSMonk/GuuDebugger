package extended.String

fun String.toSafeUpperCase() = if (equals(toLowerCase())) toUpperCase() else this