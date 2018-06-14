package com.ssouris.randomlocations.sinks

import com.ssouris.randomlocations.model.Flight
import com.ssouris.randomlocations.model.MobileCoordinates
import org.springframework.stereotype.Component

@Component
class WebSocketSink(private val webSocketServer: WebSocketServer) {

    companion object {
        const val FLIGHTS = "flights"
        const val MOBILE_COORDINATES = "mobileCoordinates"
    }

    fun sendFlights(flights: List<Flight>) = send(FLIGHTS, flights)

    fun sendMobileCoordinates(mobileCoordinates: List<MobileCoordinates>) = send(MOBILE_COORDINATES, mobileCoordinates)

    fun send(channel: String, objects: List<Any>) = eventBus().sendEvent(channel, objects)

    private fun eventBus() = webSocketServer.server.broadcastOperations


}

