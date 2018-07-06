---
title: MatchController.getMatchById - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [getMatchById](./get-match-by-id.html)

# getMatchById

`@GetMapping(["application/json", "application/xml"], ["/{id}"]) @ResponseBody fun getMatchById(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @RequestParam(false, "includePieceSets", "true") includePieceSets: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, @RequestParam(false, "includeHistory", "true") includeHistory: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`>`

Handles the GET request for the URI /matches/{id}

### Parameters

`id` - Identifier for a single match

`includePieceSets` - Returned match contains the pieceSets

`includeHistory` - Returned match contains the history (list of draws)

**Author**
Felix Dimmel

**Return**
Single match by an id

**Since**
1.0.0

