import org.example.repository.DataStorage
import org.example.service.RecalculateState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RecalculateStateTest {
    private val repo = DataStorage()
    private val service = RecalculateState(repo)

    @Test
    fun test1() {
        service.addProducts("g1", "p1", 5)
        service.addProducts("g1", "p1", 3)
        val product = repo.getProductsByGroupId("g1")?.get("p1")
        assertNotNull(product)
        assertEquals(8, product.quantity)
    }

    @Test
    fun test2() {
        service.addProducts("g1", "a", 10)
        service.addProducts("g1", "b", 5)
        service.removeProducts("g1", 6)

        val products = repo.getProductsByGroupId("g1")!!
        assertEquals(9, products["a"]?.quantity)
        assertNull(products["b"])
    }

    @Test
    fun test3() {
        service.addProducts("g1", "a", 10)
        service.addProducts("g1", "b", 3)
        service.removeProducts("g1", 3)

        val products = repo.getProductsByGroupId("g1")!!
        assertNull(products["b"])
        assertEquals(10, products["a"]?.quantity)
    }

    @Test
    fun test4() {
        service.addProducts("g1", "a", 3)
        service.addProducts("g1", "b", 2)
        service.removeProducts("g1", 10)

        val products = repo.getProductsByGroupId("g1")!!
        assertNull(products["a"])
        assertEquals(-5, products["b"]?.quantity)
    }

    @Test
    fun test5() {
        service.addProducts("g1", "a", 5)
        service.addProducts("g2", "b", 10)
        service.removeProducts("g1", 3)
        service.removeProducts("g2", 20)

        val g1 = repo.getProductsByGroupId("g1")!!
        assertEquals(2, g1["a"]?.quantity)

        val g2 = repo.getProductsByGroupId("g2")!!
        assertEquals(-10, g2["b"]?.quantity)
    }
}