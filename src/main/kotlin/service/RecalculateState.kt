package org.example.service

import org.example.di.RepositoryPattern
import org.example.model.Operation
import org.example.model.Product
import java.util.SortedMap

class RecalculateState(
    val repository : RepositoryPattern
) {
    fun addProducts(groupId: String, productId: String, quantity: Long){
        var q : Long = 0
        if(repository.getAllGroups().contains(groupId)){
            val products = repository.getProductsByGroupId(groupId)
            if(products!!.contains(productId)){
                q = products[productId]!!.quantity
            }
        }

        val newQuantity = quantity + q
        if (newQuantity == 0L) {
            // Если стало 0, удаляем товар
            repository.deleteProduct(Product(groupId, productId, 0))
        } else {
            repository.saveProduct(Product(groupId, productId, newQuantity))
        }
    }

    fun removeProducts(groupId: String, quantity: Long){
        val products : SortedMap<String, Product>? = repository.getProductsByGroupId(groupId)
        val upperKey = products?.keys?.last()
        if (products != null && !products.isEmpty()) {
            var remaining = quantity
            for (productId in products.keys.sortedDescending()) {
                val currentProduct = products[productId] ?: continue

                if (currentProduct.quantity > remaining) {
                    repository.saveProduct(Product(groupId = currentProduct.groupId,
                        productId = currentProduct.productId,
                        quantity = currentProduct.quantity-remaining
                    ))
                    return
                }else{
                    remaining -= currentProduct.quantity
                    repository.deleteProduct(currentProduct)
                }
            }

            if(remaining > 0){
                repository.saveProduct(Product(
                    groupId = groupId,
                    productId = upperKey!!,
                    quantity = -remaining
                ))
            }
        }
        else{
            repository.saveProduct(Product(
                groupId = groupId,
                productId = "__default__",
                quantity = -quantity
            ))
        }
    }
}