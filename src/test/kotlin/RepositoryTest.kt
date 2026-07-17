import org.example.model.Product
import org.example.repository.DataStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RepositoryTest {
    private val repo = DataStorage()

    @Test
    fun test1() {
        val product = Product("g1", "p1", 5)
        repo.saveProduct(product)
        val products = repo.getProductsByGroupId("g1")
        assertNotNull(products)
        assertEquals(1, products.size)
        assertEquals(5, products["p1"]?.quantity)
    }

    @Test
    fun test2() {
        repo.saveProduct(Product("g1", "p1", 5))
        repo.saveProduct(Product("g1", "p1", 10))
        val product = repo.getProductsByGroupId("g1")?.get("p1")
        assertNotNull(product)
        assertEquals(10, product.quantity) // не 15
    }

    @Test
    fun test3() {
        repo.saveProduct(Product("g1", "b", 1))
        repo.saveProduct(Product("g1", "a", 2))
        val products = repo.getProductsByGroupId("g1")!!
        val keys = products.keys.toList()
        assertEquals(listOf("a", "b"), keys)
    }

    @Test
    fun test4() {
        val product = Product("g1", "p1", 0)
        val deleted = repo.deleteProduct(product)
        assertNull(deleted)
    }

    @Test
    fun test5() {
        repo.saveProduct(Product("g1", "p1", 1))
        repo.saveProduct(Product("g2", "p2", 2))
        val groups = repo.getAllGroups()
        assertEquals(setOf("g1", "g2"), groups)
    }
}