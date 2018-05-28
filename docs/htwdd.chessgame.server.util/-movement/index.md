---
title: Movement - 
---

[htwdd.chessgame.server.util](../index.html) / [Movement](./index.html)

# Movement

`interface Movement`

Interface for piece movement

**Author**
Felix Dimmel

**Since**
1.0.0

### Functions

| [getMovementFields](get-movement-fields.html) | `abstract fun getMovementFields(movementFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Calculate all possible movement fields for a special piece |
| [getThreadedFields](get-threaded-fields.html) | `abstract fun getThreadedFields(threatedFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Calculate all possible threated fields for a special piece |

### Inheritors

| [MovementUtility](../-movement-utility/index.html) | `abstract class MovementUtility : `[`Movement`](./index.md)<br>Abstract class for piece movement |

