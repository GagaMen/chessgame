---
title: DrawController.addDraw - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](index.html) / [addDraw](./add-draw.html)

# addDraw

`@PostMapping(["application/x-www-form-urlencoded"], ["application/json", "application/xml"]) @ResponseStatus(HttpStatus.CREATED) fun addDraw(@RequestParam matchId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @RequestParam drawCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @RequestParam(false) startColumn: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null, @RequestParam(false) startRow: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`? = null): `[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)

Handles the POST request for the URI /draws
Params encoded as application/x-www-form-urlencode

### Parameters

`matchId` - Match reference

`drawCode` - SAN code to indicate draw properties

`startColumn` - Value to indicate the column of start field

`startRow` - Value to indicate the row of start field

**Author**
Felix Dimmel

**Return**
Created draw

**Since**
1.0.0

