package org.example

import org.example.di.RepositoryPattern
import org.example.model.OperationType
import org.example.repository.DataStorage
import org.example.service.ParsingService
import org.example.service.RecalculateState
import org.example.service.ReportService
import java.io.File
import java.io.FileNotFoundException

fun main(args : Array<String>) {
    val repository : RepositoryPattern = DataStorage()
    val parsing = ParsingService()
    val recalculate = RecalculateState(repository)
    val report = ReportService(repository)

    val inputPath = args.getOrElse(0) { "interactionFiles/input.csv" }
    val outputPath = args.getOrElse(1) { "interactionFiles/output.csv" }

    var lineIndex = 0
    println("Program started...")
    try {
        File(inputPath).bufferedReader().useLines { lines ->
            println("Start reading from $inputPath")
            lines.forEach { line ->
                try {
                    val operation = parsing.parseToOperation(line)

                    when (operation?.type) {
                        OperationType.ADD -> recalculate.addProducts(
                            operation.groupId,
                            operation.productId!!,// из определения парсера
                            operation.quantity
                        )

                        OperationType.DELETE -> recalculate.removeProducts(
                            operation.groupId,
                            operation.quantity
                        )

                        else -> {
                            throw Exception("Operation type not found")
                        }
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.message} at line $lineIndex")
                }

                lineIndex++
            }
            println("Been processed $lineIndex lines")
            println("End reading from $inputPath")
        }

        File(outputPath).bufferedWriter().use { writer ->
            println("Start writing from $outputPath")
            report.writeResult(writer)
            println("End writing from $outputPath")
        }
        println("Program result saved in $outputPath!")
    }catch (e: FileNotFoundException){
        println("Exception: File not found! Please check both file paths")
    }
}
