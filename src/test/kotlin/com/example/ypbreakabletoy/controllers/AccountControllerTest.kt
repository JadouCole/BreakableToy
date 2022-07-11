package com.example.ypbreakabletoy.controllers

import com.example.ypbreakabletoy.json.entities.interfaces.JsonFailureResponse
import com.example.ypbreakabletoy.json.entities.interfaces.JsonSuccessResponse
import com.example.ypbreakabletoy.models.AccountInfoResponseDTO
import com.example.ypbreakabletoy.services.AccountInfoService
import com.example.ypbreakabletoy.services.MatchHistoryService
import com.example.ypbreakabletoy.services.MatchInfoService
import com.example.ypbreakabletoy.testdata.getRandomAccountDTO
import com.example.ypbreakabletoy.testdata.getRandomMatchHistoryFromInt
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class AccountControllerTest {

        private val mockAccountInfoService: AccountInfoService = mock()
        private val mockMatchHistoryService: MatchHistoryService = mock()
        private val mockMatchInfoService: MatchInfoService = mock()
        private val mockRuntime: java.lang.RuntimeException = mock()
        private var accountController: AccountController = AccountController(mockAccountInfoService, mockMatchHistoryService,mockMatchInfoService,"testAPI")

        @Before
        fun setup(){

        }

        @Test
        fun testGetAccountInfo_HappyPath(){
            //Given
            val testAccount = "testAccount"
            val testTag = "testTag"
            val apikey = "testAPI"
            val accountDTO = getRandomAccountDTO( gameName = testAccount, tagLine = testTag)
            val accountInfoResults = AccountInfoService.GetAccountInfoResults.Success(Mono.just(accountDTO))

            //When
            whenever(mockAccountInfoService.getAccountInfo(testAccount,testTag, apikey)).thenReturn(accountInfoResults)

            //Then
            val responseEntity = accountController.getPlayerId(testAccount,testTag)
            assertTrue(responseEntity.statusCode.is2xxSuccessful)
            val data = (responseEntity.body as JsonSuccessResponse<*>).data  as AccountInfoResponseDTO
            assertEquals(testAccount,data.gameName)
            assertEquals(testTag,data.tagLine)
        }

        @Test
        fun testGetAccountInfo_AccountNotFound(){
        val testAccount = "testAccount"
        val testTag = "testTag"
        val testAPI = "testAPI"
        val accountNotFound = "Account not Found"

        val accountDTO = AccountInfoService.GetAccountInfoResults.AccountNotFound(accountNotFound)

        //when
        whenever(mockAccountInfoService.getAccountInfo(testAccount,testTag,testAPI)).thenReturn(accountDTO)
        val responseEntity = accountController.getPlayerId(testAccount,testTag)

        //then
        assertTrue(responseEntity.statusCode.is4xxClientError)
        val response = responseEntity.body as JsonFailureResponse

        assertEquals("failure", response.status)
        assertEquals(accountNotFound, response.message)
    }

        @Test
        fun testGetMatchHistory_HappyPath(){
            //Given
            val testNumberOfMatches = 15
            val testPlayerId = "testPlayerId"
            val testAPI = "testAPI"
            val testMatchHistoryResponseDTO = getRandomMatchHistoryFromInt(numberOfMatches = testNumberOfMatches)
            val testArray : Array<String> = testMatchHistoryResponseDTO.matchHistory.toTypedArray()
            val matchHistoryInfoResults = MatchHistoryService.GetMatchHistoryResults.Success(Mono.just(testArray))

            //When
            whenever(mockMatchHistoryService.getMatchHistory(testPlayerId,testNumberOfMatches,testAPI)).thenReturn(matchHistoryInfoResults)

            //Then
            val responseEntity = accountController.getMatchHistory(testPlayerId,testNumberOfMatches)
            assertTrue(responseEntity.statusCode.is2xxSuccessful)

            val data = (responseEntity.body as JsonSuccessResponse<*>).data as Array<String>
            assertEquals(testArray,data)

        }

        @Test
        fun testGetMatchHistory_MatchesNotFound(){
            //Given
            val testNumberOfMatches = 15
            val testPlayerId = "testPlayerId"
            val testAPI = "testAPI"
            val testMatchesNotFound = "Matches not Found"
            val testMatchHistoryResponseDTO = getRandomMatchHistoryFromInt(numberOfMatches = testNumberOfMatches)
            val testMatchHistoryInfoResults = MatchHistoryService.GetMatchHistoryResults.MatchesNotFound(testMatchesNotFound)

            //When
            whenever(mockMatchHistoryService.getMatchHistory(testPlayerId,testNumberOfMatches,testAPI)).thenReturn(testMatchHistoryInfoResults)

            //Then
            val responseEntity = accountController.getMatchHistory(testPlayerId,testNumberOfMatches)

            assertTrue(responseEntity.statusCode.is4xxClientError)
            val response = responseEntity.body as JsonFailureResponse

            assertEquals("failure", response.status)
            assertEquals(testMatchesNotFound, response.message)
        }

        @Test
        fun testGetMatchInfo_HappyPath(){

        }

}