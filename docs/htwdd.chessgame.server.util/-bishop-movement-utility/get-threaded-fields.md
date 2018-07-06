---
title: BishopMovementUtility.getThreadedFields - 
---

[htwdd.chessgame.server.util](../index.html) / [BishopMovementUtility](index.html) / [getThreadedFields](./get-threaded-fields.html)

# getThreadedFields

`fun getThreadedFields(threatedFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [Movement.getThreadedFields](../-movement/get-threaded-fields.html)

Calculate all possible threated fields for a bishop piece

### Parameters

`threatedFields` - Hash set which was filled with threated fields

`row` - Row value of piece from which the threat emanate

`col` - Column value of piece from which the threat emanate

`match` - Match which contains the piece

**Author**
Felix Dimmel

**Since**
1.0.0

