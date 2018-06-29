package com.ssouris.randomlocations.configuration

import org.springframework.boot.autoconfigure.kafka.KafkaProperties

class Query {
    var kafkaProperties: KafkaProperties = KafkaProperties()
    var sql: String? = null
}
