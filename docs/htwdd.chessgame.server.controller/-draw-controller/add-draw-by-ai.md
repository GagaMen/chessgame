---
title: DrawController.addDrawByAi - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](index.html) / [addDrawByAi](./add-draw-by-ai.html)

# addDrawByAi

`@PostMapping(["application/x-www-form-urlencoded"], ["application/json", "application/xml"], ["/ai"]) @ResponseStatus(HttpStatus.CREATED) fun addDrawByAi(@RequestParam matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`

Handles the POST request for the URI /draws/ai
Params encoded as application/x-www-form-urlencode

### Parameters

`matchId` - Match reference

**Author**
Felix Dimmel

**Return**
Created draw from ai server

**Since**
1.0.0

