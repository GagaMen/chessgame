---
title: PlayerController - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](./index.html)

# PlayerController

`@RestController @RequestMapping(["/players"]) class PlayerController`

Controller to manage the match resource

### Constructors

| [&lt;init&gt;](-init-.html) | `PlayerController()`<br>Controller to manage the match resource |

### Functions

| [addPlayer](add-player.html) | `fun addPlayer(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Player`](../../htwdd.chessgame.server.model/-player/index.html)<br>Handles the POST request for the URI /players Params encoded as application/x-www-form-urlencode |
| [addPlayerWithJson](add-player-with-json.html) | `fun addPlayerWithJson(playerDTO: `[`PlayerDTO`](../../htwdd.chessgame.server.dto/-player-d-t-o/index.html)`): `[`Player`](../../htwdd.chessgame.server.model/-player/index.html)<br>Handles the POST request for the URI /players Params encoded as application/json |
| [deletePlayerById](delete-player-by-id.html) | `fun deletePlayerById(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the DELETE request for the URI /players/{id} |
| [getPlayerById](get-player-by-id.html) | `fun getPlayerById(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Player`](../../htwdd.chessgame.server.model/-player/index.html)<br>Handle the GET request for URI /matches/{id} |
| [getPlayerList](get-player-list.html) | `fun getPlayerList(): `[`PlayerHashMap`](../../htwdd.chessgame.server.model/-player-hash-map/index.html)<br>Handles the GET request for the URI /players |
| [playerByIdOptions](player-by-id-options.html) | `fun playerByIdOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /players/{id} |
| [playerOptions](player-options.html) | `fun playerOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /players |
| [updatePlayer](update-player.html) | `fun updatePlayer(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the PATCH request for the URI /players/{id} Params encoded as application/x-www-form-urlencode |
| [updatePlayerWithJson](update-player-with-json.html) | `fun updatePlayerWithJson(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, passwordDTO: `[`PasswordDTO`](../../htwdd.chessgame.server.dto/-password-d-t-o/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the PATCH request for the URI /players/{id} |

