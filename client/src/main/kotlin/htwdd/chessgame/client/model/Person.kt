package htwdd.chessgame.client.model

import htwdd.chessgame.client.lib.knockout

class Person(firstName: String,
             lastName: String) {

    var firstName: dynamic = knockout.observable(firstName)
    var lastName: dynamic = knockout.observable(lastName)

    var fullName: dynamic = knockout.pureComputed(fun(): dynamic = "${this.firstName()} ${this.lastName()}", this)
}