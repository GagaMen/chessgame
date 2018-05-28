---
title: BishopMovementUtility - 
---

[htwdd.chessgame.server.util](../index.html) / [BishopMovementUtility](./index.html)

# BishopMovementUtility

`class BishopMovementUtility : `[`MovementUtility`](../-movement-utility/index.html)

Movement utility class for the bishop piece

**Author**
Felix Dimmel

**Since**
1.0.0

### Constructors

| [&lt;init&gt;](-init-.html) | `BishopMovementUtility()`<br>Movement utility class for the bishop piece |

### Functions

| [getMovementFields](get-movement-fields.html) | `fun getMovementFields(movementFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Calculate all possible movement fields for a bishop piece |
| [getThreadedFields](get-threaded-fields.html) | `fun getThreadedFields(threatedFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Calculate all possible threated fields for a bishop piece |

### Inherited Functions

| [getFilteredMovementFields](../-movement-utility/get-filtered-movement-fields.html) | `fun getFilteredMovementFields(movementFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Filters movement fields Remove fields which cause a check |

