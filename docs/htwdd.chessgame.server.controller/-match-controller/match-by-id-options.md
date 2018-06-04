---
title: MatchController.matchByIdOptions - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [matchByIdOptions](./match-by-id-options.html)

# matchByIdOptions

`@RequestMapping([RequestMethod.OPTIONS], ["/{id}"]) fun matchByIdOptions(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

Handles the OPTIONS request for the URI /matches/{id}

Possible request methods:

* HEAD
* GET
* DELETE
* OPTIONS

### Parameters

`response` - Object that contains the response for the http request

**Author**
Felix Dimmel

**Since**
1.0.0

