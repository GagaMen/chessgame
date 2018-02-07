package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable

class Client(private var viewState: ViewState = ViewState.START,
             private var matchCount: Int = 0,
             var matches: HashMap<Int, Match> = HashMap(),
             private var playerCount: Int = 0,
             var players: HashMap<Int, Player> = HashMap()) : Observable() {


    fun changeState(viewState: ViewState) {
        this.viewState = viewState
        setChanged()
        notifyObservers(viewState)
    }

    fun addMatch(match: Match) {
        if (!matches.containsKey(matchCount)) {
            match.id = matchCount
            matches[matchCount] = match
            matchCount++
            setChanged()
            notifyObservers("updateMatchTable")
        } else {
            //todo: throw error
        }
    }

    fun removeMatch(key: Int) {
        if (matches.containsKey(key)) {
            matches.remove(key)
            setChanged()
            notifyObservers("updateMatchTable")
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
            notifyObservers("updatePlayerTable")
        } else {
            //todo: throw error
        }
    }

    fun updatePlayer(playerId: Int, password: String) {
        if (players.containsKey(playerId)) {
            players[playerId]?.password = password
            setChanged()
            notifyObservers("updatePlayerTable")
            setChanged()
            notifyObservers("resetPlayerForm")
        } else {
            //todo: throw error
        }
    }

    fun removePlayer(key: Int) {
        if (players.containsKey(key)) {
            players.remove(key)
            setChanged()
            notifyObservers("updatePlayerTable")
        } else {
            //todo: throw error
        }
    }
}