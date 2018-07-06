---
title: DrawController - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](./index.html)

# DrawController

`@RestController @RequestMapping(["/api/draws"]) class DrawController`

Controller to manage the draw resource

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `DrawController(appProperties: `[`AppProperties`](../../htwdd.chessgame.server.spring.web.config/-app-properties/index.html)`)`<br>Controller to manage the draw resource |

### Functions

| [addDraw](add-draw.html) | `fun addDraw(matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, drawCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, startColumn: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null, startRow: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`<br>Handles the POST request for the URI /draws Params encoded as application/x-www-form-urlencode |
| [addDrawByAi](add-draw-by-ai.html) | `fun addDrawByAi(matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`<br>Handles the POST request for the URI /draws/ai Params encoded as application/x-www-form-urlencode |
| [addDrawByAiWithJson](add-draw-by-ai-with-json.html) | `fun addDrawByAiWithJson(matchIdDTO: `[`MatchIdDTO`](../../htwdd.chessgame.server.dto/-match-id-d-t-o/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`<br>Handles the POST request for the URI /draws/ai Params encoded as application/json |
| [addDrawWithJson](add-draw-with-json.html) | `fun addDrawWithJson(drawDTO: `[`DrawDTO`](../../htwdd.chessgame.server.dto/-draw-d-t-o/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`<br>Handles the POST request for the URI /draws Params encoded as application/json |
| [drawAiOptions](draw-ai-options.html) | `fun drawAiOptions(response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Handles the OPTIONS request for the URI /draws/ai |
| [drawByIdOptions](draw-by-id-options.html) | `fun drawByIdOptions(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Handles the OPTIONS request for the URI /draws/{id} |
| [drawOptions](draw-options.html) | `fun drawOptions(response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Handles the OPTIONS request for the URI /draws |
| [getDrawById](get-draw-by-id.html) | `fun getDrawById(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`<br>Handle the GET request for URI /draws/{id} |
| [getDrawList](get-draw-list.html) | `fun getDrawList(response: HttpServletResponse): ResponseEntity<`[`DrawList`](../../htwdd.chessgame.server.model/-draw-list/index.html)`>`<br>Handles the GET request for the URI /draws |

