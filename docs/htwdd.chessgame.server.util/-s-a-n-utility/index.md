---
title: SANUtility - 
---

[htwdd.chessgame.server.util](../index.html) / [SANUtility](./index.html)

# SANUtility

`class SANUtility`

Utility class to handle a String as standard algebraic notation (SAN)

**Author**
Felix Dimmel

**Since**
1.0.0

### Constructors

| [&lt;init&gt;](-init-.html) | `SANUtility()`<br>Utility class to handle a String as standard algebraic notation (SAN) |

### Companion Object Functions

| [calcSANFromAIMove](calc-s-a-n-from-a-i-move.html) | `fun calcSANFromAIMove(startField: `[`Field`](../../htwdd.chessgame.server.model/-field/index.html)`, endField: `[`Field`](../../htwdd.chessgame.server.model/-field/index.html)`, checkmate: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, check: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Calculate SAN string with given information from ai move |
| [validateSAN](validate-s-a-n.html) | `fun validateSAN(draw: `[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`, match: `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Validate a given draw based on his SAN code |

