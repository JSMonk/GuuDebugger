package extended.Iterator

inline fun<T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null