package com.ssouris.randomlocations.datasource

import com.ssouris.randomlocations.model.Coordinates
import com.ssouris.randomlocations.model.Flight
import com.ssouris.randomlocations.model.MobileCoordinates
import com.ssouris.randomlocations.sinks.WebSocketSink
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Duration.of
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.ThreadLocalRandom

interface RandomDataProducer : Runnable {

    fun getInterval(): Duration

}


@Component
class FlightsRandomDataPoints(private val webSocketSink: WebSocketSink) : RandomDataProducer {

    companion object {
        fun getFlight(from: Coordinates, to: Coordinates) = Flight(from, to)
    }


    override fun run() {
        val flights = ArrayList<Flight>()
        val current = ThreadLocalRandom.current()
        for (i in 0 until current.nextInt(5) + 1) {
            val from = Coordinates(getNextDouble(current), getNextDouble(current))
            val to = Coordinates(getNextDouble(current), getNextDouble(current))
            flights.add(getFlight(from, to))
        }
        webSocketSink.sendFlights(flights)
    }

    override fun getInterval(): Duration = of(10, ChronoUnit.SECONDS)
}

@Component
class MobileCoordinatesRandomDataPoints(private val webSocketSink: WebSocketSink) : RandomDataProducer {

    override fun run() {
        val mobileCoordinates = ArrayList<MobileCoordinates>()
        val current = ThreadLocalRandom.current()
        for (i in 0 until current.nextInt(5) + 1) {
            val coordinates = Coordinates(getNextDouble(current), getNextDouble(current))
            mobileCoordinates.add(MobileCoordinates.from(coordinates))
        }
        webSocketSink.sendMobileCoordinates(mobileCoordinates)
    }


    override fun getInterval(): Duration = of(10, ChronoUnit.SECONDS)

}

private fun getNextDouble(current: ThreadLocalRandom) =
        current.nextDouble(-90.0, 90.0)
