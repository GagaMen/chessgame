package htwdd.chessgame.client.util

fun String.toPair(): Pair<Int, Int>? {
    val regex = "^\\(([1-8]), ([1-8])\\)$".toRegex()
    val matchResult = regex.find(this) ?: return null

    val row = matchResult.groups[1]?.value?.toIntOrNull() ?: return null
    val col = matchResult.groups[2]?.value?.toIntOrNull() ?: return null

    if (row < 1 || row > 8 || col < 1 || col > 8) return null
    return Pair(row, col)
}