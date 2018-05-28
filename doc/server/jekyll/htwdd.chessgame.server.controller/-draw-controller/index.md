---
title: DrawController - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](./index.html)

# DrawController

`@RestController @RequestMapping(["/draws"]) class DrawController`

Controller to manage the draw resource

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `DrawController()`<br>Controller to manage the draw resource |

### Functions

| [addDraw](add-draw.html) | `fun addDraw(matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, drawCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, startColumn: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null, startRow: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null): `[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)<br>Handles the POST request for the URI /draws Params encoded as application/x-www-form-urlencode |
| [addDrawWithJson](add-draw-with-json.html) | `fun addDrawWithJson(drawDTO: `[`DrawDTO`](../../htwdd.chessgame.server.dto/-draw-d-t-o/index.html)`): `[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)<br>Handles the POST request for the URI /draws Params encoded as application/json |
| [drawByIdOptions](draw-by-id-options.html) | `fun drawByIdOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /draws/{id} |
| [drawOptions](draw-options.html) | `fun drawOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /draws |
| [getDrawById](get-draw-by-id.html) | `fun getDrawById(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)<br>Handle the GET request for URI /draws/{id} |
| [getDrawList](get-draw-list.html) | `fun getDrawList(): `[`DrawList`](../../htwdd.chessgame.server.model/-draw-list/index.html)<br>Handles the GET request for the URI /draws |

