---
title: PlayerController.playerByIdOptions - 
---

[htwdd.chessgame.server.controller](../index.html) / [PlayerController](index.html) / [playerByIdOptions](./player-by-id-options.html)

# playerByIdOptions

`@RequestMapping([RequestMethod.OPTIONS], ["/{id}"]) fun playerByIdOptions(response: HttpServletResponse): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Handles the OPTIONS request for the URI /players/{id}

Possible request methods:

* HEAD
* GET
* PATCH
* DELETE
* OPTIONS

### Parameters

`response` - Object that contains the response for the http request

**Author**
Felix Dimmel

**Since**
1.0.0

