package com.example.ypbreakabletoy.services

import com.example.ypbreakabletoy.config.AccountAPIConfig
import com.example.ypbreakabletoy.models.AccountInfoResponseDTO
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI

@Service
open class AccountInfoService(
    private val accountApiConfig: AccountAPIConfig,
    @org.springframework.context.annotation.Lazy
    @Qualifier(AccountAPIConfig.TEMPLATE_NAME) private val webClient: WebClient
) {
    fun getAccountInfo(playerName: String, playerTag: String, apikey: String): GetAccountInfoResults {

        val uri: URI =
            UriComponentsBuilder.fromUriString("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/${playerName}/${playerTag}?api_key=${apikey}")
                .build().toUri()


        return try {
            val accountInfo = webClient.get().uri(uri).retrieve().bodyToMono(AccountInfoResponseDTO::class.java)
            GetAccountInfoResults.Success(accountInfo = accountInfo)
        }catch( ex: WebClientResponseException){
            GetAccountInfoResults.AccountNotFound(
                message = "Account not Found"
            )
        }
    }

    sealed class GetAccountInfoResults{
        data class Success(val accountInfo: Mono<AccountInfoResponseDTO>): GetAccountInfoResults()
        data class AccountNotFound(val message: String): GetAccountInfoResults()
    }
}