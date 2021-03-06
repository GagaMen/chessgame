---
title: Match - 
---

[htwdd.chessgame.server.model](../index.html) / [Match](./index.html)

# Match

`data class Match`

Represent a chess match

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `Match(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, players: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Player`](../-player/index.html)`> = HashMap(), playerWhite: `[`Player`](../-player/index.html)`? = null, playerBlack: `[`Player`](../-player/index.html)`? = null, pieceSets: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`PieceSet`](../-piece-set/index.html)`> = hashMapOf(WHITE to PieceSet(), BLACK to PieceSet()), currentColor: `[`PieceColor`](../-piece-color/index.html)` = WHITE, history: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Draw`](../-draw/index.html)`> = mutableListOf(), kingsideCastling: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`> = hashMapOf(WHITE to true, BLACK to true), queensideCastling: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`> = hashMapOf(WHITE to true, BLACK to true), enPassantField: `[`Field`](../-field/index.html)`? = null, halfMoves: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, check: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`> = hashMapOf(WHITE to false, BLACK to false), checkmate: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, matchCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")`<br>Represent a chess match |

### Properties

| [check](check.html) | `var check: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>`<br>Hash map of check value for each player. True if player is in check otherwise false. |
| [checkmate](checkmate.html) | `var checkmate: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>True if one player is checkmate |
| [currentColor](current-color.html) | `var currentColor: `[`PieceColor`](../-piece-color/index.html)<br>Color of player who is on the move. |
| [enPassantField](en-passant-field.html) | `var enPassantField: `[`Field`](../-field/index.html)`?`<br>Contains the field to throw en passant or null. |
| [halfMoves](half-moves.html) | `var halfMoves: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Number of halfmoves since the last movement of a pawn or the last thrown piece. |
| [history](history.html) | `val history: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Draw`](../-draw/index.html)`>`<br>Mutable list of played draws. |
| [id](id.html) | `val id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>The ID of this match. (Autogenerated by SQLite database) |
| [kingsideCastling](kingside-castling.html) | `var kingsideCastling: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>`<br>Hash map of possibility for kingside castling of each player. |
| [matchCode](match-code.html) | `var matchCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>All match information as Forsyth-Edwards-Notation (FEN) |
| [pieceSets](piece-sets.html) | `var pieceSets: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`PieceSet`](../-piece-set/index.html)`>`<br>Hash map of the pieceSets of the two players. Key: Color of player; Value: PieceSet |
| [players](players.html) | `val players: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Player`](../-player/index.html)`>`<br>Hash map of the players. Key: Color of player; Value: Player |
| [queensideCastling](queenside-castling.html) | `var queensideCastling: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`PieceColor`](../-piece-color/index.html)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>`<br>Hash map of possibility for queenside castling of each player. |

### Functions

| [setPieceSetsByMatchCode](set-piece-sets-by-match-code.html) | `fun setPieceSetsByMatchCode(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Sets all match properties by match code (FEN) |
| [updateByDraw](update-by-draw.html) | `fun updateByDraw(draw: `[`Draw`](../-draw/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Update match properties by given draw |

