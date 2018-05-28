---
title: DrawDTO - 
---

[htwdd.chessgame.server.dto](../index.html) / [DrawDTO](./index.html)

# DrawDTO

`data class DrawDTO : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)

Data transfer object for a draw

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `DrawDTO(matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, drawCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", startColumn: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null, startRow: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null)`<br>Data transfer object for a draw |

### Properties

| [drawCode](draw-code.html) | `val drawCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>SAN code to indicate draw properties |
| [matchId](match-id.html) | `val matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Match reference |
| [startColumn](start-column.html) | `val startColumn: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`?`<br>Value to indicate the column of start field |
| [startRow](start-row.html) | `val startRow: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`?`<br>Value to indicate the row of start field |

