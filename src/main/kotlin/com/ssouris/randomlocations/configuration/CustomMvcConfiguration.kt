package com.ssouris.randomlocations.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class CustomMvcConfiguration : WebFluxConfigurer {

    @Value("classpath:/static/index.html")
    private lateinit var indexHtml: Resource

    @Bean
    fun mainRouter() = router {
        GET("/") {
            ServerResponse.ok().contentType(MediaType.TEXT_HTML).syncBody(indexHtml)
        }
    }
}
