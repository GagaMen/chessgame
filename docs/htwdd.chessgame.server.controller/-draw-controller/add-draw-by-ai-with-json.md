---
title: DrawController.addDrawByAiWithJson - 
---

[htwdd.chessgame.server.controller](../index.html) / [DrawController](index.html) / [addDrawByAiWithJson](./add-draw-by-ai-with-json.html)

# addDrawByAiWithJson

`@PostMapping(["application/json"], ["application/json", "application/xml"], ["/ai"]) @ResponseStatus(HttpStatus.CREATED) fun addDrawByAiWithJson(@RequestBody matchIdDTO: `[`MatchIdDTO`](../../htwdd.chessgame.server.dto/-match-id-d-t-o/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Draw`](../../htwdd.chessgame.server.model/-draw/index.html)`>`

Handles the POST request for the URI /draws/ai
Params encoded as application/json

### Parameters

`matchIdDTO` - Contains params as data transfer object

**Author**
Felix Dimmel

**See Also**

[addDrawByAi](add-draw-by-ai.html)

[MatchIdDTO](../../htwdd.chessgame.server.dto/-match-id-d-t-o/index.html)

**Return**
Created draw from ai server

**Since**
1.0.0

