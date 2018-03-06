package htwdd.chessgame.client.util

import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest

class RequestUtility {
    companion object {
        fun get(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("GET", url)
            request.send()
        }

        fun post(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("POST", url)
            request.send()
        }

        fun put(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("PUT", url)
            request.send()
        }

        fun patch(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("PATCH", url)
            request.send()
        }

        fun delete(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("DELETE", url)
            request.send()
        }

        fun head(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("HEAD", url)
            request.send()
        }

        fun options(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("OPTIONS", url)
            request.send()
        }
    }
}