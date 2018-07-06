---
title: PlayerController.deletePlayerById - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [deletePlayerById](./delete-player-by-id.html)

# deletePlayerById

`@DeleteMapping(["application/json", "application/xml"], ["/{id}"]) fun deletePlayerById(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

Handles the DELETE request for the URI /players/{id}

### Parameters

`id` - Identifier of the player to be deleted

**Author**
Felix Dimmel

**Since**
1.0.0

