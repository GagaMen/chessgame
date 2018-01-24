package htwdd.chessgame.client.util

interface ActionListener : EventListener {
    fun actionPerformed(e: Any, arg: Any? = null)
}