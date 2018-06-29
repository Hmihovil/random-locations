package com.ssouris.randomlocations

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@ComponentScan(excludeFilters = [
    ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = arrayOf("com\\.ssouris\\.randomlocations\\.datasource\\.(random|kafka).*")
    )]
)
@SpringBootApplication
class RandomLocationsApplication

fun main(args: Array<String>) {
    runApplication<RandomLocationsApplication>(*args)
}

@Configuration
@ConditionalOnProperty("mode", havingValue = "random")
@ComponentScan("com.ssouris.randomlocations.datasource.random")
class RandomDataPointsEnabler {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RandomDataPointsEnabler::class.java)
    }

    init {
        log.info("Enabling Random Data Datasource")
    }

}

@Configuration
@ConditionalOnProperty("mode", havingValue = "kafka")
@ComponentScan("com.ssouris.randomlocations.datasource.kafka")
class KafkaDataPointsEnabler {

    companion object {
        val log: Logger = LoggerFactory.getLogger(RandomDataPointsEnabler::class.java)
    }

    init {
        log.info("Enabling Kafka Datasource")
    }

}
