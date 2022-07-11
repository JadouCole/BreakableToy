package com.example.ypbreakabletoy.controllers

import com.example.ypbreakabletoy.json.entities.interfaces.JsonResponse
import com.example.ypbreakabletoy.json.failure
import com.example.ypbreakabletoy.json.success
import com.example.ypbreakabletoy.models.*
import com.example.ypbreakabletoy.repositories.CharacterRepository
import com.example.ypbreakabletoy.services.AccountInfoService
import com.example.ypbreakabletoy.services.AccountInfoService.GetAccountInfoResults
import com.example.ypbreakabletoy.services.MatchHistoryService
import com.example.ypbreakabletoy.services.MatchHistoryService.GetMatchHistoryResults
import com.example.ypbreakabletoy.services.MatchInfoService
import com.example.ypbreakabletoy.services.MatchInfoService.GetMatchInfoResults
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/tft")
class AccountController(
    private val accountInfoService: AccountInfoService,
    private val matchHistoryService: MatchHistoryService,
    private val matchInfoService: MatchInfoService,
    @Value("\${api.key}")
    val apiKey : String,
) {
    @Autowired
    lateinit var characterRepository: CharacterRepository
    private val logger = LoggerFactory.getLogger(AccountController::class.java)

    @CrossOrigin
    @GetMapping("/championlist")
    fun getChampionList(): MutableList<CharacterModel> {
        return characterRepository.findAll()
    }

    @CrossOrigin
    @GetMapping("/compute/info/{playerName}/{playerTag}/{numberOfMatches}")
    fun computeInfo(@PathVariable("playerName")playerName: String,@PathVariable("playerTag")playerTag: String, @PathVariable("numberOfMatches")numberOfMatches: Int): ComputedStatsDTO {
        val testplayerID : AccountInfoResponseDTO? = handleGetAccountInfoResultsForMatchInfo(accountInfoService.getAccountInfo("jadocole","doug",apiKey))
        var averagePlacement = 0L;
        val mostCommonTrait = "null"
        if(testplayerID!= null) {
            val playerIdResponse = testplayerID.puuid
            logger.warn(playerIdResponse)
            val testMatchHistory = handleGetMatchHistoryResultsForMatchInfo(
                matchHistoryService.getMatchHistory(
                    testplayerID.puuid,
                    numberOfMatches,
                    apiKey
                )
            )
            if (testMatchHistory != null) {
                val arrayOfMatches = testMatchHistory.forEach { e ->
                    val matchInfoResponse =
                        handleGetMatchInfoResultsCompute(matchInfoService.getMatchInfo(playerIdResponse, e, apiKey))
                    if (matchInfoResponse != null) {
                        averagePlacement += matchInfoResponse.placement
                    }
                }
            }
        }
        return ComputedStatsDTO(averagePlacement = (averagePlacement/numberOfMatches), playerName = playerName, mostCommonTrait = mostCommonTrait, id= 1)
    }

    @CrossOrigin
    @GetMapping("/accountinfo/{playerName}/{playerTag}")
    fun getPlayerId(@PathVariable("playerName") playerName:String,@PathVariable("playerTag")playerTag:String): ResponseEntity<JsonResponse> {
        return handleGetAccountInfoResults(accountInfoService.getAccountInfo(playerName,playerTag, apiKey))
    }

    @CrossOrigin
    @GetMapping("/matchhistory/{playerId}/{numberOfMatches}")
    fun getMatchHistory(@PathVariable(required = true, value = "playerId") playerId:String,@PathVariable(required = false, value = "numberOfMatches") numberOfMatches: Int): ResponseEntity<JsonResponse> {
        return handleGetMatchHistoryResults(matchHistoryService.getMatchHistory(playerId, numberOfMatches,apiKey))
    }

    @CrossOrigin
    @GetMapping("/matchinfo/{playerId}/{matchNumber}")
    fun getMatchInfo(@PathVariable("playerId") playerId:String, @PathVariable("matchNumber") matchNumber:String): ResponseEntity<JsonResponse>? {
        val testplayerID : AccountInfoResponseDTO? = handleGetAccountInfoResultsForMatchInfo(accountInfoService.getAccountInfo("jadocole","doug",apiKey))
        if(testplayerID!= null){
            val playerIdResponse  = testplayerID.puuid
            logger.warn(playerIdResponse)
            return  handleGetMatchInfoResults(matchInfoService.getMatchInfo(playerIdResponse,matchNumber,apiKey))
        }
        return null
    }

    fun handleGetAccountInfoResults(result: GetAccountInfoResults): ResponseEntity<JsonResponse> {
        return when(result){
            is GetAccountInfoResults.Success -> ResponseEntity.ok(success(data= result.accountInfo.block()))
            is GetAccountInfoResults.AccountNotFound -> {
                logger.warn(result.message)
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(failure(message = result.message))
            }
        }
    }
    fun handleGetAccountInfoResultsForMatchInfo(result: GetAccountInfoResults): AccountInfoResponseDTO? {
        return when(result){
            is GetAccountInfoResults.Success -> result.accountInfo.block()
            is GetAccountInfoResults.AccountNotFound -> {
                null
            }
        }
    }

    fun handleGetMatchHistoryResults(result: GetMatchHistoryResults): ResponseEntity<JsonResponse> {
        return when(result){
            is GetMatchHistoryResults.Success -> ResponseEntity.ok(success(data= result.matchHistory.block()))
            is GetMatchHistoryResults.MatchesNotFound -> {
                logger.warn(result.message)
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(failure(message = result.message))
            }
        }
    }
    fun handleGetMatchHistoryResultsForMatchInfo(result: GetMatchHistoryResults): Array<String>? {
        return when(result){
            is GetMatchHistoryResults.Success -> result.matchHistory.block()
            is GetMatchHistoryResults.MatchesNotFound ->{
                null
            }
        }
    }

    fun handleGetMatchInfoResults(result: GetMatchInfoResults): ResponseEntity<JsonResponse> {

        return when(result){
            is GetMatchInfoResults.Success -> ResponseEntity.ok(success(data = parseMatchInfoResponse(result.matchInfo, result.playerID)))
            is GetMatchInfoResults.MatchesNotFound -> {
                logger.warn(result.message)
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(failure(message = result.message))
            }
        }
    }
    fun handleGetMatchInfoResultsCompute(result:GetMatchInfoResults): Participant? {
        return when(result){
            is GetMatchInfoResults.Success -> parseMatchInfoResponse(result.matchInfo,result.playerID)
            is GetMatchInfoResults.MatchesNotFound -> {
                null
            }
        }
    }

    fun parseMatchInfoResponse(result: Mono<MatchInfoResponseDTO>, playerId :String ): Participant? {
        result.block()?.info?.participants?.forEach{ participant ->
            if(participant.puuid == playerId){
                return participant
            }
        }
        return null
    }
}