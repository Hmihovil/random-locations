package com.ssouris.randomlocations.sinks

import com.fasterxml.jackson.databind.JsonNode
import com.ssouris.randomlocations.Flight
import com.ssouris.randomlocations.MobileCoordinates
import com.ssouris.randomlocations.ql.KafkaTopicQueryLanguage
import org.springframework.stereotype.Component

@Component
class WebSocketJsonSink(val webSocketSink: WebSocketSink) {

    fun send(records: List<Pair<KafkaTopicQueryLanguage, JsonNode>>) =
            records.groupBy { it.first.target }
                    .forEach { k, v ->
                        println("send to $k -> ${v.size}")
                        when (k) {
                            WebSocketSink.FLIGHTS -> sendFlightsJSON(flightDataMapper(v))
                            WebSocketSink.MOBILE_COORDINATES -> sendMobileCoordinatesJSON(mobileCoordinatesDataMapper(v))
                        }
                    }

    fun sendMobileCoordinatesJSON(mappedMobileCoordinates: List<MobileCoordinates>) =
            webSocketSink.sendMobileCoordinates(mappedMobileCoordinates)


    fun sendFlightsJSON(flights: List<Flight>) = webSocketSink.sendFlights(flights)


    fun flightDataMapper(flights: List<Pair<KafkaTopicQueryLanguage, JsonNode>>) = flights
            .map { it.component2() }
            .map { Flight.from(it) }
            .toList()


    fun mobileCoordinatesDataMapper(mobileCoordinates: List<Pair<KafkaTopicQueryLanguage, JsonNode>>) =
            mobileCoordinates.map { it.component2() }
                    .map { MobileCoordinates.from(it) }
                    .toList()

}
