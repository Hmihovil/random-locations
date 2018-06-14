package com.ssouris.randomlocations.sinks

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import org.springframework.context.SmartLifecycle
import org.springframework.stereotype.Component

@Component
class WebSocketServer : SmartLifecycle {

    final val server: SocketIOServer

    private var isRunning: Boolean = false

    init {
        val config = Configuration()
        config.hostname = "localhost"
        config.port = 9093
        server = SocketIOServer(config)
    }

    override fun isAutoStartup() = true

    override fun stop(callback: Runnable) {
        callback.run()
        stop()
    }

    override fun start() {
        server.start()
        isRunning = true
    }

    override fun stop() {
        server.stop()
        isRunning = false
    }

    override fun isRunning() = isRunning

    override fun getPhase() = 0

}
