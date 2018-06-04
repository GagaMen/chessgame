---
title: PlayerController.updatePlayer - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [updatePlayer](./update-player.html)

# updatePlayer

`@PatchMapping(["application/x-www-form-urlencoded"], ["application/json", "application/xml"], ["/{id}"]) fun updatePlayer(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @RequestParam password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

Handles the PATCH request for the URI /players/{id}
Params encoded as application/x-www-form-urlencode

### Parameters

`id` - Identifier for a single player

`password` - The updated password of the player

**Author**
Felix Dimmel

**Since**
1.0.0

