package htwdd.chessgame.client

import htwdd.chessgame.client.lib.knockout
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Person

fun main(args: Array<String>) {
    val client = Client()
    client.helloClient()

    knockout.applyBindings(Person("Felix", "Dimmel"))
}