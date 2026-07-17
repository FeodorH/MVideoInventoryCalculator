package org.example.model

data class Operation (
    val type : OperationType,
    val groupId : String,
    val productId: String? = null,
    val quantity : Long,
)

enum class OperationType {ADD, DELETE}