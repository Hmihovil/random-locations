package com.ssouris.randomlocations.datasource.random

import com.ssouris.randomlocations.Coordinates
import com.ssouris.randomlocations.Flight
import com.ssouris.randomlocations.nextCoordinate
import com.ssouris.randomlocations.sinks.WebSocketSink
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.concurrent.ThreadLocalRandom

@Component
class FlightsRandomDataPoints(private val webSocketSink: WebSocketSink) : RandomDataProducer {

    companion object {
        fun getFlight(from: Coordinates, to: Coordinates) = Flight(from, to)
    }

    override fun run() {
        val flights = ArrayList<Flight>()
        val current = ThreadLocalRandom.current()
        for (i in 0 until current.nextInt(5) + 1) {
            val from = current.nextCoordinate()
            val to = current.nextCoordinate()
            flights.add(getFlight(from, to))
        }
        webSocketSink.sendFlights(flights)
    }

    override fun getInterval(): Duration = Duration.of(10, ChronoUnit.SECONDS)
}
