---
title: Match.<init> - 
---

[htwdd.chessgame.server.model](../index.html) / [Match](index.html) / [&lt;init&gt;](./-init-.html)

# &lt;init&gt;

`Match(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, players: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Player`](../-player/index.html)`> = HashMap(), playerWhite: `[`Player`](../-player/index.html)`? = null, playerBlack: `[`Player`](../-player/index.html)`? = null, pieceSets: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`PieceSet`](../-piece-set/index.html)`> = hashMapOf(WHITE to PieceSet(), BLACK to PieceSet()), currentColor: `[`PieceColor`](../-piece-color/index.html)` = WHITE, history: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Draw`](../-draw/index.html)`> = mutableListOf(), kingsideCastling: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`> = hashMapOf(WHITE to true, BLACK to true), queensideCastling: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`> = hashMapOf(WHITE to true, BLACK to true), enPassantField: `[`Field`](../-field/index.html)`? = null, halfMoves: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, check: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`> = hashMapOf(WHITE to false, BLACK to false), checkmate: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, matchCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")`

Represent a chess match

**Author**
Felix Dimmel

