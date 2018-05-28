---
title: DrawController.getDrawById - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](index.html) / [getDrawById](./get-draw-by-id.html)

# getDrawById

`@GetMapping(["application/json", "application/xml"], ["/{id}"]) fun getDrawById(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)

Handle the GET request for URI /draws/{id}

### Parameters

`id` - Identifier for a single draw

**Author**
Felix Dimmel

**Return**
Single draw by an id

**Since**
1.0.0

