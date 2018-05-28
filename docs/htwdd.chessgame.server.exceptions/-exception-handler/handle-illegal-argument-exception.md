---
title: ExceptionHandler.handleIllegalArgumentException - 
---

[htwdd.chessgame.server.exceptions](../index.html) / [ExceptionHandler](index.html) / [handleIllegalArgumentException](./handle-illegal-argument-exception.html)

# handleIllegalArgumentException

`@ExceptionHandler([IllegalArgumentException]) @ResponseStatus(HttpStatus.NOT_FOUND) fun handleIllegalArgumentException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)

Handles IllegalArgumentException

### Parameters

`ex` - IllegalArgumentException

`request` - Http request object

**Author**
Felix Dimmel

**Return**
ErrorResponseObject

**Since**
1.0.0

