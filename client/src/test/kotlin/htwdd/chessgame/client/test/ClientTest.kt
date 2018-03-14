package htwdd.chessgame.client.test

import htwdd.chessgame.client.model.Client
import kotlin.test.BeforeTest

class ClientTest() {

    var classUnderTest: Client? = null

    @BeforeTest
    fun setup() {
        classUnderTest = Client()
    }
}