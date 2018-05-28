---
title: ExceptionHandler.handleRuntimeException - 
---

[htwdd.chessgame.server.exceptions](../index.html) / [ExceptionHandler](index.html) / [handleRuntimeException](./handle-runtime-exception.html)

# handleRuntimeException

`@ExceptionHandler([RuntimeException]) @ResponseStatus(HttpStatus.CONFLICT) fun handleRuntimeException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)

Handles RuntimeException

### Parameters

`ex` - RuntimeException

`request` - Http request object

**Author**
Felix Dimmel

**Return**
ErrorResponseObject

**Since**
1.0.0

