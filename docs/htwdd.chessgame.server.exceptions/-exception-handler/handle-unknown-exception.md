---
title: ExceptionHandler.handleUnknownException - 
---

[htwdd.chessgame.server.exceptions](../index.html) / [ExceptionHandler](index.html) / [handleUnknownException](./handle-unknown-exception.html)

# handleUnknownException

`@ExceptionHandler([Exception]) @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) fun handleUnknownException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)

Handles all unforeseen exception

### Parameters

`ex` - InternalServerError

`request` - Http request object

**Author**
Felix Dimmel

**Return**
ErrorResponseObject

**Since**
1.0.0

