---
title: ExceptionHandler - 
---

[htwdd.chessgame.server.exceptions](../index.html) / [ExceptionHandler](./index.html)

# ExceptionHandler

`@RestControllerAdvice class ExceptionHandler`

Handler object to manage exceptions

### Constructors

| [&lt;init&gt;](-init-.html) | `ExceptionHandler()`<br>Handler object to manage exceptions |

### Functions

| [handleBadRequestException](handle-bad-request-exception.html) | `fun handleBadRequestException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)<br>Handles BadRequestException |
| [handleIllegalArgumentException](handle-illegal-argument-exception.html) | `fun handleIllegalArgumentException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)<br>Handles IllegalArgumentException |
| [handleRuntimeException](handle-runtime-exception.html) | `fun handleRuntimeException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)<br>Handles RuntimeException |
| [handleUnknownException](handle-unknown-exception.html) | `fun handleUnknownException(ex: `[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`, request: WebRequest): `[`ErrorResponseObject`](../-error-response-object/index.html)<br>Handles all unforeseen exception |

