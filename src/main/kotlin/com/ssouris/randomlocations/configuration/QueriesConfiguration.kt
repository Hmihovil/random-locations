package com.ssouris.randomlocations.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueriesConfiguration {

    @Bean
    @ConfigurationProperties
    fun queriesProperties() = QueriesProperties()

}
