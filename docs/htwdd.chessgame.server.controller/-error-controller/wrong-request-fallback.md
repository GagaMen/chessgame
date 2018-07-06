---
title: ErrorController.wrongRequestFallback - 
---

[htwdd.chessgame.server.controller](../index.html) / [ErrorController](index.html) / [wrongRequestFallback](./wrong-request-fallback.html)

# wrongRequestFallback

`@RequestMapping([RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS], ["application/json", "application/xml"], ["*", "**/*"]) fun wrongRequestFallback(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Handles all request on undefined entry points

### Exceptions

`BadRequestException` -

**Author**
Felix Dimmel

**Since**
1.0.0

