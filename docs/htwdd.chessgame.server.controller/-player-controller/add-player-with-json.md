---
title: PlayerController.addPlayerWithJson - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [addPlayerWithJson](./add-player-with-json.html)

# addPlayerWithJson

`@PostMapping(["application/json"], ["application/json", "application/xml"]) @ResponseStatus(HttpStatus.CREATED) @ResponseBody fun addPlayerWithJson(@RequestBody playerDTO: `[`PlayerDTO`](../../htwdd.chessgame.server.dto/-player-d-t-o/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Player`](../../htwdd.chessgame.server.model/-player/index.html)`>`

Handles the POST request for the URI /players
Params encoded as application/json

### Parameters

`playerDTO` - Contains params as data transfer object

**Author**
Felix Dimmel

**See Also**

[addPlayer](add-player.html)

[PlayerDTO](../../htwdd.chessgame.server.dto/-player-d-t-o/index.html)

**Return**
Created draw

**Since**
1.0.0

