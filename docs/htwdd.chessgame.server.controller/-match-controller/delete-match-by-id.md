---
title: MatchController.deleteMatchById - 
---

[htwdd.chessgame.server.controller](../index.html) / [MatchController](index.html) / [deleteMatchById](./delete-match-by-id.html)

# deleteMatchById

`@DeleteMapping(["application/json", "application/xml"], ["/{id}"]) fun deleteMatchById(@PathVariable id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, response: HttpServletResponse): ResponseEntity<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

Handles the DELETE request for the URI /matches/{id}

### Parameters

`id` - Identifier of the match to be deleted

**Author**
Felix Dimmel

**Since**
1.0.0

