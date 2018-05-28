---
title: ErrorResponseObject - 
---

[htwdd.chessgame.server.exceptions](../index.html) / [ErrorResponseObject](./index.html)

# ErrorResponseObject

`class ErrorResponseObject`

Object to represent a clearly and user comprehensible error message

**Author**
Felix Dimmel

### Constructors

| [&lt;init&gt;](-init-.html) | `ErrorResponseObject(timestamp: `[`Date`](http://docs.oracle.com/javase/6/docs/api/java/util/Date.html)` = Date(), statusCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 500, error: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR, exception: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "No message available", path: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "")`<br>Object to represent a clearly and user comprehensible error message |

### Properties

| [error](error.html) | `val error: HttpStatus`<br>HTTP error |
| [exception](exception.html) | `val exception: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Name of thrown exception |
| [message](message.html) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Error message |
| [path](path.html) | `val path: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>URI at which the error was thrown |
| [statusCode](status-code.html) | `val statusCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>HTTP status code |
| [timestamp](timestamp.html) | `val timestamp: `[`Date`](http://docs.oracle.com/javase/6/docs/api/java/util/Date.html)<br>Timestamp of the thrown error |

### Functions

| [toString](to-string.html) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Cast the error response object to String (JSON) Necessary to return the error as JSON if the client has requested |

