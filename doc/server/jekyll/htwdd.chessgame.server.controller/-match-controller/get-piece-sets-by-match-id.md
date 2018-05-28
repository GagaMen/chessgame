---
title: MatchController.getPieceSetsByMatchId - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [getPieceSetsByMatchId](./get-piece-sets-by-match-id.html)

# getPieceSetsByMatchId

`@GetMapping(["application/json", "application/xml"], ["/{id}/pieceSets"]) fun getPieceSetsByMatchId(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`PieceSetHashMap`](../../htwdd.chessgame.server.model/-piece-set-hash-map/index.html)

Handles the GET request for the URI /matches/{id}/pieceSets

### Parameters

`id` - Identifier for a single match

**Author**
Felix Dimmel

**Return**
Hash map of pieceSets of the two players

**Since**
1.0.0

