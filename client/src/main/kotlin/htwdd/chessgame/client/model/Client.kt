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
        if (matches.containsKey(matchCount)) return

        match.id = matchCount
        matches[matchCount] = match
        matchCount++
        setChanged()
        notifyObservers("updateMatchTable")
    }

    fun removeMatch(key: Int) {
        if (!matches.containsKey(key)) return

        matches.remove(key)
        setChanged()
        notifyObservers("updateMatchTable")
    }

    fun addPlayer(player: Player) {
        if (players.containsKey(playerCount)) return

        player.id = playerCount
        players[playerCount] = player
        playerCount++
        setChanged()
        notifyObservers("updatePlayerTable")
    }

    fun updatePlayer(playerId: Int, password: String) {
        if (!players.containsKey(playerId)) return

        players[playerId]?.password = password
        setChanged()
        notifyObservers("updatePlayerTable")
        setChanged()
        notifyObservers("resetPlayerForm")
    }

    fun removePlayer(key: Int) {
        if (!players.containsKey(key)) return

        players.remove(key)
        setChanged()
        notifyObservers("updatePlayerTable")
    }
}