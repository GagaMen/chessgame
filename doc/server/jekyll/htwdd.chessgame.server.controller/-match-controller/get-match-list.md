---
title: MatchController.getMatchList - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [getMatchList](./get-match-list.html)

# getMatchList

`@GetMapping(["application/json", "application/xml"]) fun getMatchList(@RequestParam(false, "includePieceSets", "true") includePieceSets: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, @RequestParam(false, "includeHistory", "true") includeHistory: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`MatchHashMap`](../../htwdd.chessgame.server.model/-match-hash-map/index.html)

Handles the GET request for the URI /matches

### Parameters

`includePieceSets` - Returned match contains the pieceSets

`includeHistory` - Returned match contains the history (list of draws)

**Author**
Felix Dimmel

**Return**
Hash map of matches

**Since**
1.0.0

