package compiler

import exceptions.Position

sealed class Entity {
    class Variable(var value: Any): Entity()
    class Function(val name: String, val body: List<OperationCode>): Entity()
}

class SymbolTable(private val table: HashMap<String, Entity> = hashMapOf()) {
    operator fun iterator() = table.iterator()

    fun getFunctionWithName(name: String, position: Position): Entity.Function {
        if (table.containsKey(name)) {
            return table[name] as? Entity.Function ?: throw FunctionIsNotDefinedException(name, position)
        }
        throw FunctionIsNotDefinedException(name, position)
    }

    fun getVariableWithName(name: String, position: Position): Entity.Variable {
        if (table.containsKey(name)) {
            return table[name] as? Entity.Variable ?: throw VariableIsNotDefinedException(name, position)
        }
        throw VariableIsNotDefinedException(name, position)
    }

    fun setOrCreateVariableWithName(name: String, value: Any, position: Position) {
        if (table.containsKey(name)) {
            val existed = table[name] as Entity
            if (existed !is Entity.Variable) {
                throw IllegalAssignException("Can't assign value to ${existed.javaClass.name}", position)
            }
            existed.value = value
        } else {
            table[name] = Entity.Variable(value)
        }
    }

    fun createFunction(name: String, function: Entity.Function, position: Position) {
        if (table.containsKey(name)) {
            when (val existed = table[name] as Entity) {
                is Entity.Function -> throw IllegalAssignException("Function with name '$name' already exists", position)
                is Entity.Variable -> throw IllegalAssignException("There are variable with name '$name'", position)
            }
        }
        table[name] = function
    }
}