package com.ssouris.randomlocations.datasource.kafka

import com.ssouris.randomlocations.configuration.QueriesProperties
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaDatasources : InitializingBean {

    @Autowired
    lateinit var queriesProperties: QueriesProperties

    @Autowired
    lateinit var kafkaConsumerFactory: KafkaConsumerFactory

    override fun afterPropertiesSet() {
        queries().forEachIndexed { i, query ->
            kafkaConsumerFactory.register("kafkaConsumer$i", query)
        }
    }

    private fun queries() = queriesProperties.queries

}
