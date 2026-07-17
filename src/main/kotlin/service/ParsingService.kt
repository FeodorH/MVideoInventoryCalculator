package org.example.service

import org.example.model.Operation
import org.example.model.OperationType

class ParsingService {
    fun parseToOperation(line : String) : Operation? {
        val line = line.trim()
        val list = line.split(';')

        when (list.size) {
            3 -> {
                val groupId = list[0]
                val productId = list[1]
                val quantity = list[2].toLong()
                return Operation(OperationType.ADD, groupId, productId, quantity)
            }

            2 -> {
                val groupId = list[0]
                val quantity = list[1].toLong()
                return Operation(OperationType.DELETE, groupId, quantity = quantity)
            }

            else -> {
                throw _root_ide_package_.kotlin.IllegalArgumentException("Invalid string")
            }
        }
        return null
    }
}