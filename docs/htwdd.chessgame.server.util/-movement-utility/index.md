---
title: MovementUtility - 
---

[htwdd.chessgame.server.util](../index.html) / [MovementUtility](./index.html)

# MovementUtility

`abstract class MovementUtility : `[`Movement`](../-movement/index.html)

Abstract class for piece movement

**Author**
Felix Dimmel

**Since**
1.0.0

### Constructors

| [&lt;init&gt;](-init-.html) | `MovementUtility()`<br>Abstract class for piece movement |

### Functions

| [getFilteredMovementFields](get-filtered-movement-fields.html) | `fun getFilteredMovementFields(movementFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Filters movement fields Remove fields which cause a check |

### Inherited Functions

| [getMovementFields](../-movement/get-movement-fields.html) | `abstract fun getMovementFields(movementFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Calculate all possible movement fields for a special piece |
| [getThreadedFields](../-movement/get-threaded-fields.html) | `abstract fun getThreadedFields(threatedFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Calculate all possible threated fields for a special piece |

### Inheritors

| [BishopMovementUtility](../-bishop-movement-utility/index.html) | `class BishopMovementUtility : `[`MovementUtility`](./index.md)<br>Movement utility class for the bishop piece |
| [KingMovementUtility](../-king-movement-utility/index.html) | `class KingMovementUtility : `[`MovementUtility`](./index.md)<br>Movement utility class for the king piece |
| [KnightMovementUtility](../-knight-movement-utility/index.html) | `class KnightMovementUtility : `[`MovementUtility`](./index.md)<br>Movement utility class for the knight piece |
| [PawnMovementUtility](../-pawn-movement-utility/index.html) | `class PawnMovementUtility : `[`MovementUtility`](./index.md)<br>Movement utility class for the pawn piece |
| [QueenMovementUtility](../-queen-movement-utility/index.html) | `class QueenMovementUtility : `[`MovementUtility`](./index.md)<br>Movement utility class for the queen piece Combined the rules of bishop and rook |
| [RookMovementUtility](../-rook-movement-utility/index.html) | `class RookMovementUtility : `[`MovementUtility`](./index.md)<br>Movement utility class for the rook piece |

