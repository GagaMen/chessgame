package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Piece
import htwdd.chessgame.client.model.PieceSet
import htwdd.chessgame.client.model.Player

fun String.toPair(): Pair<Int, Int>? {
    val regex = "^\\(([1-8]), ([1-8])\\)$".toRegex()
    val matchResult = regex.find(this) ?: return null

    val row = matchResult.groups[1]?.value?.toIntOrNull() ?: return null
    val col = matchResult.groups[2]?.value?.toIntOrNull() ?: return null

    if (row < 1 || row > 8 || col < 1 || col > 8) return null
    return Pair(row, col)
}

inline fun <reified T1, reified T2> HashMap<T1, T2>.copy(): HashMap<T1, T2>? {
    val copyOfHashMap = HashMap<T1, T2>()

    // change this expression to T::class.isdata
    // At this time the reflection api isn't supported for js
    // @see https://kotlinlang.org/docs/reference/js-reflection.html
    when (T2::class.simpleName) {
        Player::class.simpleName -> {
            this.forEach { (key, value) ->
                copyOfHashMap[key] = ((value as Player).copy()) as T2
            }
            return copyOfHashMap
        }
        PieceSet::class.simpleName -> {
            this.forEach { (key, value) ->
                copyOfHashMap[key] = ((value as PieceSet).deepCopy()) as T2
            }
            return copyOfHashMap
        }
        Piece::class.simpleName -> {
            this.forEach { (key, value) ->
                copyOfHashMap[key] = ((value as Piece).deepCopy()) as T2
            }
            return copyOfHashMap
        }
    }

    return null
}