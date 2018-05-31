package htwdd.chessgame.client.util

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class PollingUtility {
    private var stopPolling = true

    fun start(pollingTask: () -> Unit) {
        stopPolling = false
        sendPollingRequest(pollingTask)
    }

    fun stop() {
        stopPolling = true
    }

    private fun sendPollingRequest(pollingTask: () -> Unit) {
        if (stopPolling) return
        launch {
            pollingTask()
            delay(5000)
            sendPollingRequest(pollingTask)
        }
    }
}