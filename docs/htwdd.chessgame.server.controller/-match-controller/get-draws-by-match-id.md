---
title: MatchController.getDrawsByMatchId - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [getDrawsByMatchId](./get-draws-by-match-id.html)

# getDrawsByMatchId

`@GetMapping(["application/json", "application/xml"], ["/{id}/draws"]) fun getDrawsByMatchId(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`DrawList`](../../htwdd.chessgame.server.model/-draw-list/index.html)

Handles the GET request for the URI /matches/{id}/draws

### Parameters

`id` - Identifier for a single match

**Author**
Felix Dimmel

**Return**
List of draws of a match

**Since**
1.0.0

