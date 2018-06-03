package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class DrawList(val draws: MutableList<Draw>)