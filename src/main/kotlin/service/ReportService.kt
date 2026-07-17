package org.example.service

import org.example.di.RepositoryPattern
import java.io.Writer

class ReportService(
    val repository : RepositoryPattern
) {
    fun writeResult(writer: Writer){
        val groups = repository.getAllGroups().sorted()
        for(groupId in groups){
            val products = repository.getProductsByGroupId(groupId)
            if(products != null) {
                for (productId in products.keys) {
                    if(products[productId] != null) {
                        val resultString = "${products[productId]?.groupId};$productId;${products[productId]?.quantity}\n"
                        writer.write(resultString)
                    }
                }
            }
        }
    }
}