---
title: DrawController.addDrawWithJson - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](index.html) / [addDrawWithJson](./add-draw-with-json.html)

# addDrawWithJson

`@PostMapping(["application/json"], ["application/json", "application/xml"]) @ResponseStatus(HttpStatus.CREATED) fun addDrawWithJson(@RequestBody drawDTO: `[`DrawDTO`](../../htwdd.chessgame.server.dto/-draw-d-t-o/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`

Handles the POST request for the URI /draws
Params encoded as application/json

### Parameters

`drawDTO` - Contains params as data transfer object

**Author**
Felix Dimmel

**See Also**

[addDraw](add-draw.html)

[DrawDTO](../../htwdd.chessgame.server.dto/-draw-d-t-o/index.html)

**Return**
Created draw

**Since**
1.0.0

