---
title: PieceSet - 
---

[htwdd.chessgame.server.model](../index.html) / [PieceSet](./index.html)

# PieceSet

`class PieceSet : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)

Represent a set of chess pieces

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `PieceSet(activePieces: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, `[`Piece`](../-piece/index.html)`> = HashMap(), capturedPieces: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Piece`](../-piece/index.html)`> = HashSet())`<br>Represent a set of chess pieces |

### Properties

| [activePieces](active-pieces.html) | `var activePieces: `[`HashMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, `[`Piece`](../-piece/index.html)`>`<br>Contains all active pieces. Key: Position of Piece as Pair; Value: Piece |
| [capturedPieces](captured-pieces.html) | `var capturedPieces: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Piece`](../-piece/index.html)`>`<br>Contains all captured pieces |

