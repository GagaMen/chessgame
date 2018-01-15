package htwdd.chessgame.client.test

import htwdd.chessgame.client.Client
import kotlin.test.*

class ClientTest {

    @BeforeTest
    val classUnderTest = Client()


    @Test
    fun testFoo() {
        assertEquals(10, classUnderTest.foo())
    }

    @Test
    fun testFooWrong() {
        assertEquals(20, classUnderTest.foo())
    }
}