---
title: MatchController.addMatch - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [addMatch](./add-match.html)

# addMatch

`@PostMapping(["application/x-www-form-urlencoded"], ["application/json", "application/xml"]) @ResponseStatus(HttpStatus.CREATED) fun addMatch(@RequestParam playerWhiteId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @RequestParam playerBlackId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Match`](../../htwdd.chessgame.server.model/-match/index.html)`>`

Handles the POST request for the URI /matches
Params encoded as application/x-www-form-urlencode

### Parameters

`playerWhiteId` - Player reference for the color white

`playerBlackId` - Player reference for the color black

**Author**
Felix Dimmel

**Return**
Created draw

**Since**
1.0.0

