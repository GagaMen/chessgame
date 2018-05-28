---
title: MatchController - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](./index.html)

# MatchController

`@RestController @RequestMapping(["/matches"]) class MatchController`

Controller to manage the match resource

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `MatchController()`<br>Controller to manage the match resource |

### Functions

| [addMatch](add-match.html) | `fun addMatch(playerWhiteId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, playerBlackId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)<br>Handles the POST request for the URI /matches Params encoded as application/x-www-form-urlencode |
| [addMatchWithJson](add-match-with-json.html) | `fun addMatchWithJson(matchDTO: `[`MatchDTO`](../../htwdd.chessgame.server.dto/-match-d-t-o/index.html)`): `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)<br>Handles the POST request for the URI /draws Params encoded as application/json |
| [deleteMatchById](delete-match-by-id.html) | `fun deleteMatchById(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the DELETE request for the URI /matches/{id} |
| [drawsByMatchOptions](draws-by-match-options.html) | `fun drawsByMatchOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /matches/{id}/draws |
| [getDrawsByMatchId](get-draws-by-match-id.html) | `fun getDrawsByMatchId(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`DrawList`](../../htwdd.chessgame.server.model/-draw-list/index.html)<br>Handles the GET request for the URI /matches/{id}/draws |
| [getMatchById](get-match-by-id.html) | `fun getMatchById(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, includePieceSets: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, includeHistory: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)<br>Handles the GET request for the URI /matches/{id} |
| [getMatchList](get-match-list.html) | `fun getMatchList(includePieceSets: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, includeHistory: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`MatchHashMap`](../../htwdd.chessgame.server.model/-match-hash-map/index.html)<br>Handles the GET request for the URI /matches |
| [getPieceSetsByMatchId](get-piece-sets-by-match-id.html) | `fun getPieceSetsByMatchId(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`PieceSetHashMap`](../../htwdd.chessgame.server.model/-piece-set-hash-map/index.html)<br>Handles the GET request for the URI /matches/{id}/pieceSets |
| [matchByIdOptions](match-by-id-options.html) | `fun matchByIdOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /matches/{id} |
| [matchOptions](match-options.html) | `fun matchOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /matches |
| [pieceSetsByMatchOptions](piece-sets-by-match-options.html) | `fun pieceSetsByMatchOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Handles the OPTIONS request for the URI /matches/{id}/pieceSets |

