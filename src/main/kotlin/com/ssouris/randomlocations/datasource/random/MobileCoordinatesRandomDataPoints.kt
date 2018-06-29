package com.ssouris.randomlocations.datasource.random

import com.ssouris.randomlocations.MobileCoordinates
import com.ssouris.randomlocations.nextCoordinate
import com.ssouris.randomlocations.sinks.WebSocketSink
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.concurrent.ThreadLocalRandom

@Component
class MobileCoordinatesRandomDataPoints(private val webSocketSink: WebSocketSink) : RandomDataProducer {

    override fun run() {
        val mobileCoordinates = ArrayList<MobileCoordinates>()
        val current = ThreadLocalRandom.current()
        for (i in 0 until current.nextInt(5) + 1) {
            mobileCoordinates.add(MobileCoordinates.from(current.nextCoordinate()))
        }
        webSocketSink.sendMobileCoordinates(mobileCoordinates)
    }

    override fun getInterval(): Duration = Duration.of(10, ChronoUnit.SECONDS)

}
