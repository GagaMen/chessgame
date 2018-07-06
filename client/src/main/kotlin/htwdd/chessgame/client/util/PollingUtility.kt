package htwdd.chessgame.client.util

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class PollingUtility {
    private var stopPolling = true

    fun start(delayTime: Int, pollingTask: () -> Unit) {
        stopPolling = false
        sendPollingRequest(delayTime, pollingTask)
    }

    fun stop() {
        stopPolling = true
    }

    private fun sendPollingRequest(delayTime: Int, pollingTask: () -> Unit) {
        if (stopPolling) return
        launch {
            pollingTask()
            delay(delayTime)
            sendPollingRequest(delayTime, pollingTask)
        }
    }
}