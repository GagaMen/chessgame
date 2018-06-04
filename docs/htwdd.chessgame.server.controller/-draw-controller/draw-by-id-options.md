---
title: DrawController.drawByIdOptions - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](index.html) / [drawByIdOptions](./draw-by-id-options.html)

# drawByIdOptions

`@RequestMapping([RequestMethod.OPTIONS], ["/{id}"]) fun drawByIdOptions(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

Handles the OPTIONS request for the URI /draws/{id}

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

