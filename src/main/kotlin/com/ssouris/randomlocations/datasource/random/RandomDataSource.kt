package com.ssouris.randomlocations.datasource.random

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

@Component
class RandomDataSource(val randomDataProducers: List<RandomDataProducer>) : InitializingBean, DisposableBean {

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(2)

    override fun afterPropertiesSet() {
        for (dataProducer in randomDataProducers) {
            val randomInterval = ThreadLocalRandom.current().nextLong(1, dataProducer.getInterval().seconds)
            scheduler.scheduleAtFixedRate(dataProducer, 0, randomInterval, TimeUnit.SECONDS)
        }
    }

    override fun destroy() {
        scheduler.shutdown()
    }

}
