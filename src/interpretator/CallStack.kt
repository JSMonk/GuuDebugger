package interpretator

import compiler.Entity
import exceptions.Position
import java.util.ArrayDeque

const val MAX_STACK_SIZE = 200

class CallStack : ArrayDeque<Entity.Function>() {
    fun push(f: Entity.Function, position: Position) {
        if (size >= MAX_STACK_SIZE) {
            throw StackOverflowException(position)
        }
        super.push(f)
    }
}

