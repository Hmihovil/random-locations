package com.ssouris.randomlocations.datasource

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.SmartLifecycle
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Component
@ConditionalOnProperty("mode", havingValue = "random")
class RandomDataSource(val randomDataProducers: List<RandomDataProducer>) : SmartLifecycle {

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(2)
    private var running: Boolean = false

    @PostConstruct
    fun postInit() {

        for (dataProducer in randomDataProducers) {
            val randomInterval = ThreadLocalRandom.current().nextLong(1, dataProducer.getInterval().seconds)
            scheduler.scheduleAtFixedRate(dataProducer, 0, randomInterval, TimeUnit.SECONDS)
        }
    }

    override fun isRunning(): Boolean {
        return running
    }

    override fun start() {
        running = true
    }

    override fun isAutoStartup() = true

    override fun stop(callback: Runnable) {
        callback.run()
        stop()
    }

    override fun stop() {
        scheduler.shutdown()
        this.running = false
    }

    override fun getPhase() = 1

}
