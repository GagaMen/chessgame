---
title: PlayerController.updatePlayerWithJson - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [updatePlayerWithJson](./update-player-with-json.html)

# updatePlayerWithJson

`@PatchMapping(["application/json"], ["application/json", "application/xml"], ["/{id}"]) fun updatePlayerWithJson(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @RequestBody passwordDTO: `[`PasswordDTO`](../../htwdd.chessgame.server.dto/-password-d-t-o/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Handles the PATCH request for the URI /players/{id}

### Parameters

`id` - Identifier for a single player

`passwordDTO` - Contains params as data transfer object

**Author**
Felix Dimmel

**See Also**

[updatePlayer](update-player.html)

[PasswordDTO](../../htwdd.chessgame.server.dto/-password-d-t-o/index.html)

**Since**
1.0.0

