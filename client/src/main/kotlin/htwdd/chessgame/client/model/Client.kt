package htwdd.chessgame.client.model

class Client(var matches: Map<Int, Match> = emptyMap(),
             var players: Map<Int, Player> = emptyMap()) {

    fun helloClient() {
        println("Hello Client")
    }

    fun foo() = 10
}