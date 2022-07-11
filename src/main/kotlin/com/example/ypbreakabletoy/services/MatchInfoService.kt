package com.example.ypbreakabletoy.services

import com.example.ypbreakabletoy.config.MatchInfoAPIConfig
import com.example.ypbreakabletoy.models.MatchInfoResponseDTO

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI

@Service
class MatchInfoService(
    private val matchInfoAPIConfig: MatchInfoAPIConfig,
    @org.springframework.context.annotation.Lazy
    @Qualifier(MatchInfoAPIConfig.TEMPLATE_NAME) private val webclient:WebClient
) {

    fun getMatchInfo(playerId:String, matchNumber:String, apikey: String): GetMatchInfoResults{
        val uri: URI = UriComponentsBuilder.fromUriString("https://americas.api.riotgames.com/tft/match/v1/matches/${matchNumber}?api_key=${apikey}")
            .build().toUri()
        return try{
            val matchInfo = webclient.get().uri(uri).retrieve().bodyToMono(MatchInfoResponseDTO::class.java)
            GetMatchInfoResults.Success(matchInfo = matchInfo, playerID = playerId)
        }catch(ex: WebClientResponseException){
            GetMatchInfoResults.MatchesNotFound(
                message = "Match not Found"
            )
        }
    }
    sealed class GetMatchInfoResults{
        data class Success(val matchInfo: Mono<MatchInfoResponseDTO>, val playerID: String): GetMatchInfoResults()
        data class MatchesNotFound(val message: String): GetMatchInfoResults()
    }
}