---
title: RookMovementUtility.getMovementFields - 
---

[htwdd.chessgame.server.util](../index.html) / [RookMovementUtility](index.html) / [getMovementFields](./get-movement-fields.html)

# getMovementFields

`fun getMovementFields(movementFields: `[`HashSet`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-set/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>>, row: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, col: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [Movement.getMovementFields](../-movement/get-movement-fields.html)

Calculate all possible movement fields for a rook piece

### Parameters

`movementFields` - Hash set which was filled with movement fields

`row` - Row value of piece which should be moved

`col` - Column value of piece which should be moved

`match` - Match which contains the piece

**Author**
Felix Dimmel

**Since**
1.0.0

