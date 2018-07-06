---
title: SANUtility.calcSANFromAIMove - 
---

[htwdd.chessgame.server.util](../index.html) / [SANUtility](index.html) / [calcSANFromAIMove](./calc-s-a-n-from-a-i-move.html)

# calcSANFromAIMove

`fun calcSANFromAIMove(startField: `[`Field`](../../htwdd.chessgame.server.model/-field/index.html)`, endField: `[`Field`](../../htwdd.chessgame.server.model/-field/index.html)`, checkmate: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, check: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Calculate SAN string with given information from ai move

### Parameters

`startField` - Start position of moved piece

`endField` - End position of moved piece

`checkmate` - True if one player is checkmate otherwise false

`check` - True if one player is in check otherwise false

`match` - The match reference of the draw

**Author**
Felix Dimmel

**Since**
1.0.0

