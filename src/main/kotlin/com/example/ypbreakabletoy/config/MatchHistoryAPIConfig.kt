package com.example.ypbreakabletoy.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class MatchHistoryAPIConfig(
    @Value("\${match-api.base-url}") val baseUrl: String,
    @Value("\${match-api.getMatches}") val getMatches: String,
    @Value("\${match-api.readTimeoutMs}") val readTimout: Long,
    @Value("\${match-api.connectTimeoutMs}") val connectTimeout: Long) {

    companion object{
        const val TEMPLATE_NAME = "matchHistoryAPIWebClient"
    }

    @Bean(name = [TEMPLATE_NAME])
    open fun buildWebClientTemplate(builder : WebClient.Builder): WebClient {
        return builder.build()
    }
}