package com.example.ypbreakabletoy.services

import com.example.ypbreakabletoy.config.MatchHistoryAPIConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI


@Service
class MatchHistoryService(
    private val matchHistoryAPIConfig: MatchHistoryAPIConfig,
    @org.springframework.context.annotation.Lazy
    @Qualifier(MatchHistoryAPIConfig.TEMPLATE_NAME) private val webClient: WebClient
) {

    fun getMatchHistory(playerId:String, numberOfMatches: Int, apikey:String): GetMatchHistoryResults{
        val uri: URI = UriComponentsBuilder.fromUriString("https://americas.api.riotgames.com/tft/match/v1/matches/by-puuid/${playerId}/ids?count=${numberOfMatches}&api_key=${apikey}")
            .build().toUri()

        return try{
            val matchHistory = webClient.get().uri(uri).retrieve().bodyToMono(Array<String>::class.java)
            GetMatchHistoryResults.Success(matchHistory = matchHistory)
        }catch (ex: WebClientResponseException){
            GetMatchHistoryResults.MatchesNotFound(
                message = "Matches not Found"
            )
        }
    }

    sealed class GetMatchHistoryResults{
        data class Success(val matchHistory: Mono<Array<String>>): GetMatchHistoryResults()
        data class MatchesNotFound(val message: String): GetMatchHistoryResults()
    }
}