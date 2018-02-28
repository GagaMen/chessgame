package htwdd.chessgame.client.view

import htwdd.chessgame.client.util.Observer

interface View : Observer {
    fun render()
}