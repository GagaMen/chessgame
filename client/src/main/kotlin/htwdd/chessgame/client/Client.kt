package htwdd.chessgame.client

class Client(var matches: Map<Int, Match> = emptyMap(),
             var players: Map<Int, Player> = emptyMap()) {
    fun helloClient() {
        println("Hello Client")
    }
}