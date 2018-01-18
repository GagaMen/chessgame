package htwdd.chessgame.client.test

import htwdd.chessgame.client.model.Client
import kotlin.test.*

class ClientTest() {

    var classUnderTest: Client? = null

    @BeforeTest
    fun setup() {
        classUnderTest = Client()
    }

    @Test
    fun testFoo() {
        assertEquals(10, classUnderTest?.foo())
    }

    @Test
    fun testFooWrong() {
        assertEquals(20, classUnderTest?.foo())
    }
}