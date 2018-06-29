package com.ssouris.randomlocations.sinks

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class WebSocketServer : InitializingBean, DisposableBean {

    var server: SocketIOServer? = null

    override fun afterPropertiesSet() {
        val config = Configuration()
        config.hostname = "localhost"
        config.port = 9093
        server = SocketIOServer(config)
        server?.start()
    }

    override fun destroy() {
        server?.stop()
    }

}
