---
title: MatchController.addMatchWithJson - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [addMatchWithJson](./add-match-with-json.html)

# addMatchWithJson

`@PostMapping(["application/json"], ["application/json", "application/xml"]) @ResponseStatus(HttpStatus.CREATED) fun addMatchWithJson(@RequestBody matchDTO: `[`MatchDTO`](../../htwdd.chessgame.server.dto/-match-d-t-o/index.html)`): `[`Match`](../../htwdd.chessgame.server.model/-match/index.html)

Handles the POST request for the URI /draws
Params encoded as application/json

### Parameters

`matchDTO` - Contains params as data transfer object

**Author**
Felix Dimmel

**See Also**

[addMatch](add-match.html)

[MatchDTO](../../htwdd.chessgame.server.dto/-match-d-t-o/index.html)

**Return**
Created draw

**Since**
1.0.0

