---
title: PlayerController.getPlayerById - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [getPlayerById](./get-player-by-id.html)

# getPlayerById

`@GetMapping(["application/json", "application/xml"], ["/{id}"]) @ResponseBody fun getPlayerById(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Player`](../../htwdd.chessgame.server.model/-player/index.html)

Handle the GET request for URI /matches/{id}

### Parameters

`id` - Identifier for a single match

**Author**
Felix Dimmel

**Return**
Single match by an id

**Since**
1.0.0

