package htwdd.chessgame.client.model

import htwdd.chessgame.client.config.ClientConfig
import htwdd.chessgame.client.util.Observable

class Client(private var viewState: ViewState = ViewState.START,
             var matches: HashMap<Int, Match> = HashMap(),
             var players: HashMap<Int, Player> = HashMap()) : Observable() {

    var config: ClientConfig = ClientConfig()

    init {
        initObservable()
    }

    fun changeState(viewState: ViewState, arg: Any? = null) {
        this.viewState = viewState
        setChanged()
        if (arg != null) notifyObservers(arg)
        else notifyObservers(viewState)
    }

    fun addMatch(match: Match) {
        if (matches.containsKey(match.id)) return

        matches[match.id] = match
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
        if (players.containsKey(player.id)) return

        players[player.id] = player
        setChanged()
        notifyObservers("updatePlayerTable")
    }

    fun updatePlayer(playerId: Int, password: String) {
        if (!players.containsKey(playerId)) return

        players[playerId]?.password = password
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