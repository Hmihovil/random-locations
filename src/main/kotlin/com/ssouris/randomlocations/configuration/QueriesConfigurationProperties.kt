package com.ssouris.randomlocations.configuration

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties
class QueriesConfigurationProperties {

    var queries: List<Query> = ArrayList()
}

class Query {
    var kafkaProperties: KafkaProperties = KafkaProperties()
    var sql: String? = null
}
