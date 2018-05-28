---
title: ExceptionHandler.handleBadRequestException - 
---

[htwdd.chessgame.server.exceptions](../index.html) / [ExceptionHandler](index.html) / [handleBadRequestException](./handle-bad-request-exception.html)

# handleBadRequestException

`@ExceptionHandler([BadRequestException]) @ResponseStatus(HttpStatus.BAD_REQUEST) fun handleBadRequestException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)

Handles BadRequestException

### Parameters

`ex` - BadRequestException

`request` - Http request object

**Author**
Felix Dimmel

**Return**
ErrorResponseObject

**Since**
1.0.0

