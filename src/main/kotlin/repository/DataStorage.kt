package org.example.repository

import org.example.di.RepositoryPattern
import org.example.model.Product
import java.util.SortedMap
import java.util.TreeMap

class DataStorage : RepositoryPattern {

    private val data = mutableMapOf<String, TreeMap<String, Product>>()

    override fun getAllGroups(): Set<String> =
        data.keys.toSet()

    override fun getProductsByGroupId(id: String): SortedMap<String, Product>? =
        data[id]?.toSortedMap()

    override fun saveProduct(product: Product) {
        if(!data.containsKey(product.groupId)){
            val t = TreeMap<String, Product>()
            t[product.productId] = product
            data[product.groupId] = t
        }else{
            if(data[product.groupId]?.containsKey(product.productId) == false){
                data[product.groupId]?.put(product.productId, product)
            }else{
                data[product.groupId]?.put(product.productId, Product(
                    groupId = product.groupId,
                    productId = product.productId,
                    quantity = product.quantity
                ))
            }
        }
    }

    override fun deleteProduct(product: Product): Product? {
        if(data.containsKey(product.groupId)){
            if(data[product.groupId]?.containsKey(product.productId) == true){
                data[product.groupId]?.remove(product.productId)
                if(data[product.groupId]?.isEmpty() == true){
                    data.remove(product.groupId)
                }
                return product
            }else{
                return null
            }
        }else{
            return null
        }
    }

}