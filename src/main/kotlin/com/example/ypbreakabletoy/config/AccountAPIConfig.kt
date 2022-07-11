package com.example.ypbreakabletoy.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

@Configuration
open class AccountAPIConfig(
    @Value("\${account-api.base-url}") val baseUrl: String,
    @Value("\${account-api.getAccount}") val getAccount: String,
    @Value("\${account-api.readTimeoutMs}") val readTimeout: Long,
    @Value("\${account-api.connectTimeoutMs}") val connectTimeout: Int) {

    companion object{
        const val TEMPLATE_NAME = "accountApiWebClient"
    }

    @Bean(name = [TEMPLATE_NAME])
    open fun buildWebClientTemplate(builder : WebClient.Builder):WebClient{
        val client : HttpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .doOnConnected { conn -> conn.addHandler(ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)) }
        return builder.clientConnector(ReactorClientHttpConnector(client)).build()
    }
}