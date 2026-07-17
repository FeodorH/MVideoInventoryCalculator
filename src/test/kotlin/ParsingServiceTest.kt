import org.example.model.Operation
import org.example.model.OperationType
import org.example.service.ParsingService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ParsingServiceTest {
    private val parser = ParsingService()

    @Test
    fun test1(){
        val expected = Operation(OperationType.ADD,"123","456",12)
        val actual = parser.parseToOperation("123;456;12")
        assertEquals(expected, actual)
    }

    @Test
    fun test2() {
        val expected = Operation(OperationType.DELETE, "g1", null, 5)
        val actual = parser.parseToOperation("g1;5")
        assertEquals(expected, actual)
    }

    @Test
    fun test3() {
        assertFailsWith<IllegalArgumentException> {
            parser.parseToOperation("123;456;12;extra")
        }
        assertFailsWith<IllegalArgumentException> {
            parser.parseToOperation("123") // only one field
        }
    }

    @Test
    fun test4() {
        assertFailsWith<NumberFormatException> {
            parser.parseToOperation("g1;p1;abc")
        }
    }

    @Test
    fun test5() {
        val actual = parser.parseToOperation("  g1;p1;7  ")
        val expected = Operation(OperationType.ADD, "g1", "p1", 7)
        assertEquals(expected, actual)
    }
}