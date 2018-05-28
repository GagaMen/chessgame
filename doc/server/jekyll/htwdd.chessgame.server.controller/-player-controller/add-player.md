---
title: PlayerController.addPlayer - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [addPlayer](./add-player.html)

# addPlayer

`@PostMapping(["application/x-www-form-urlencoded"], ["application/json", "application/xml"]) @ResponseStatus(HttpStatus.CREATED) fun addPlayer(@RequestParam name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @RequestParam password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Player`](../../htwdd.chessgame.server.model/-player/index.html)

Handles the POST request for the URI /players
Params encoded as application/x-www-form-urlencode

### Parameters

`name` - The name of the player

`password` - The password of the player

**Author**
Felix Dimmel

**Return**
Created match

**Since**
1.0.0

