package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.view.MainView
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get

class ClientController : Controller {

    var client: Client
    val mainView: MainView

    init {
        client = loadData()
        mainView = MainView(this)

        client.addObserver(mainView)
    }

    fun loadData(): Client {
        //todo: load data from server
        return Client()
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "addPlayer" -> addPlayer(arg)
        }
    }

    fun getMatches(): HashMap<Int, Match> {
        return client.matches
    }

    fun getPlayers(): HashMap<Int, Player> {
        return client.players
    }

    fun addPlayer(arg: Any?) {
        when (arg) {
            is HTMLFormElement -> {
                val name = arg[0]
                val password = arg[1]

                if (
                        (name != null && name is HTMLInputElement && name.type == "text" && name.value != "") &&
                        (password != null && password is HTMLInputElement && password.type == "password" && password.value != "")
                ) {
                    client.addPlayer(Player(name.value, password.value))
                    name.value = ""
                    password.value = ""
                } else {
                    //todo: throw exception
                }
            }
        }
    }
}
