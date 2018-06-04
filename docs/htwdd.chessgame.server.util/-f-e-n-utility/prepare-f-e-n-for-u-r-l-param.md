---
title: FENUtility.prepareFENForURLParam - 
---

[htwdd.chessgame.server.util](../index.html) / [FENUtility](index.html) / [prepareFENForURLParam](./prepare-f-e-n-for-u-r-l-param.html)

# prepareFENForURLParam

`fun prepareFENForURLParam(fen: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Prepares a FEN String as URL parameter

Replace:

* "/" -&gt; "%2F"
* " " -&gt; "+"

### Parameters

`fen` - String  in the FEN

**Author**
Felix Dimmel

**Return**
Prepared FEN string as URL parameter with replaced characters

**Since**
1.0.0

