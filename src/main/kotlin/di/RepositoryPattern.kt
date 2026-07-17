package org.example.di

import org.example.model.Product
import java.util.SortedMap
import java.util.TreeMap

interface RepositoryPattern {
    fun getProductsByGroupId(id : String): SortedMap<String, Product>?
    fun saveProduct(product : Product)
    fun getAllGroups(): Set<String>
    fun deleteProduct(product: Product) : Product?
}