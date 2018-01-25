package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable

class Client(var matchCount: Int = 0,
             var matches: HashMap<Int, Match> = HashMap(),
             var playerCount: Int = 0,
             var players: HashMap<Int, Player> = HashMap()) : Observable() {


    fun addMatch(match: Match) {
        if (!matches.containsKey(matchCount)) {
            match.id = matchCount
            matches[matchCount] = match
            matchCount++
            setChanged()
            notifyObservers("updateMatchList")
        } else {
            //todo: throw error
        }
    }

    fun removeMatch(match: Match) {
        val key = match.hashCode()

        if (matches.containsKey(key)) {
            matches.remove(key)
        } else {
            //todo: throw error
        }
    }

    fun removeMatch(key: Int) {
        if (matches.containsKey(key)) {
            matches.remove(key)
        } else {
            //todo: throw error
        }
    }

    fun addPlayer(player: Player) {
        if (!players.containsKey(playerCount)) {
            player.id = playerCount
            players[playerCount] = player
            playerCount++
            setChanged()
            notifyObservers("updatePlayerList")
        } else {
            //todo: throw error
        }
    }

    fun removePlayer(player: Player) {
        if (players.containsKey(player.id)) {
            players.remove(player.id)
        } else {
            //todo: throw error
        }
    }

    fun removePlayer(key: Int) {
        if (players.containsKey(key)) {
            players.remove(key)
        } else {
            //todo: throw error
        }
    }
}