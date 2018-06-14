package com.ssouris.randomlocations.datasource

import com.ssouris.randomlocations.configuration.QueriesConfigurationProperties
import com.ssouris.randomlocations.sinks.WebSocketJsonSink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.context.support.GenericWebApplicationContext
import java.util.concurrent.TimeUnit
import java.util.function.Supplier
import javax.annotation.PostConstruct

@Component
@ConditionalOnProperty("mode", havingValue = "kafka")
class KafkaDataSourceRegistry {

    @Autowired
    lateinit var queriesConfigurationProperties: QueriesConfigurationProperties

    @Autowired
    lateinit var webSocketJsonSink: WebSocketJsonSink

    @Autowired
    lateinit var genericWebApplicationContext: GenericWebApplicationContext

    @PostConstruct
    fun init() {
        queriesConfigurationProperties.queries.forEachIndexed { i, query ->
            genericWebApplicationContext.registerBean(
                    "kafkaConsumer$i",
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

}
