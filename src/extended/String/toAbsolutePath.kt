package extended.String

import java.nio.file.Paths

fun String.toAbsolutePath() = Paths.get(this).toAbsolutePath().toString()

