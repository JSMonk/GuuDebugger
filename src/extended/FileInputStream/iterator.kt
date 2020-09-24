package extended.FileInputStream

import java.io.FileInputStream

class FileInputStreamIterator(private val stream: FileInputStream) : Iterator<Char> {
    var lastByte = stream.read()
    override fun hasNext() = lastByte != -1
    override fun next() =  lastByte.toChar().also { lastByte = stream.read() }
}

operator fun FileInputStream.iterator(): Iterator<Char> {
    return FileInputStreamIterator(this)
}

