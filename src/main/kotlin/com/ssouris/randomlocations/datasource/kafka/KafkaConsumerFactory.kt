package com.ssouris.randomlocations.datasource.kafka

import com.ssouris.randomlocations.configuration.Query
import com.ssouris.randomlocations.sinks.WebSocketJsonSink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.support.GenericWebApplicationContext
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

@Component
class KafkaConsumerFactory {

    @Autowired
    lateinit var webSocketJsonSink: WebSocketJsonSink

    @Autowired
    lateinit var genericWebApplicationContext: GenericWebApplicationContext

    fun register(beanName: String, query: Query) {
        genericWebApplicationContext.registerBean(
                beanName,
                KafkaConsumer::class.java,
                Supplier {
                    val kafkaConsumer = KafkaConsumer()
                    kafkaConsumer.observe(query.sql!!, query.kafkaProperties)
                            .buffer(2L, TimeUnit.SECONDS, 1000)
                            .subscribe { webSocketJsonSink.send(it) }
                    kafkaConsumer
                })
    }

}
