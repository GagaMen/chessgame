---
title: MatchController.drawsByMatchOptions - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [drawsByMatchOptions](./draws-by-match-options.html)

# drawsByMatchOptions

`@RequestMapping([RequestMethod.OPTIONS], ["/{id}/draws"]) fun drawsByMatchOptions(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

Handles the OPTIONS request for the URI /matches/{id}/draws

Possible request methods:

* HEAD
* GET
* OPTIONS

### Parameters

`response` - Object that contains the response for the http request

**Author**
Felix Dimmel

**Since**
1.0.0

