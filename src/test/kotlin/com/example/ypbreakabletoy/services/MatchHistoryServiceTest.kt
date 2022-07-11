package com.example.ypbreakabletoy.services

import com.example.ypbreakabletoy.config.MatchHistoryAPIConfig
import com.example.ypbreakabletoy.testdata.getRandomMatchHistoryFromInt
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import java.net.URI
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class MatchHistoryInfoServiceTest {
    private val mockWebClient: WebClient = mock ()
    private val mockMatchHistoryAPIConfig: MatchHistoryAPIConfig = mock ()
    private val mockRequestHeaderURI: WebClient.RequestHeadersUriSpec<*> = mock ()
    private val mockRequestHeadersSpec: WebClient.RequestHeadersSpec<*> = mock ()
    private val mockResponseSpec: WebClient.ResponseSpec = mock ()

    private var matchHistoryService: MatchHistoryService = MatchHistoryService(mockMatchHistoryAPIConfig,mockWebClient)

    @Before
    fun setup(){
        whenever(mockMatchHistoryAPIConfig.baseUrl).thenReturn("this needs to be changed")
        whenever(mockMatchHistoryAPIConfig.getMatches).thenReturn("this also needs to be changed")
    }

    @Test
    fun getMatchHistory_HappyPath(){
        //Given
        val testNumberOfMatches = 15
        val testPlayerId = "testPlayerId"
        val testAPI = "testAPI"
        val testMatchHistoryResponseDTO = getRandomMatchHistoryFromInt(numberOfMatches = testNumberOfMatches)

        val testArray: Array<String> = testMatchHistoryResponseDTO.matchHistory.toTypedArray()
        testArray
        //When
        whenever(mockWebClient.get())
            .thenReturn(mockRequestHeaderURI)
        whenever(mockRequestHeaderURI.uri(any<URI>()))
            .thenReturn(mockRequestHeadersSpec)
        whenever(mockRequestHeadersSpec.retrieve())
            .thenReturn(mockResponseSpec)
        whenever(mockResponseSpec.bodyToMono(Array<String>::class.java))
            .thenReturn(Mono.just(testArray))

        //Then
        val response = matchHistoryService.getMatchHistory(testPlayerId,testNumberOfMatches,testAPI) as MatchHistoryService.GetMatchHistoryResults.Success

        assertEquals(testMatchHistoryResponseDTO.matchHistory.toTypedArray().toList(), response.matchHistory.block()?.toList())

    }

    @Test
    fun testGetMatchHistory_ReturnsNull_WhenMatchNotFound(){
        // Given
        val testNumberOfMatches = 15
        val testPlayerId = "testPlayerId"
        val testAPI = "testAPI"
        val testMatchHistoryResponseDTO = getRandomMatchHistoryFromInt(numberOfMatches = testNumberOfMatches)

        // When
        whenever(mockWebClient.get())
            .thenReturn(mockRequestHeaderURI)
        whenever(mockRequestHeaderURI.uri(any<URI>()))
            .thenReturn(mockRequestHeadersSpec)
        whenever(mockRequestHeadersSpec.retrieve())
            .thenReturn(mockResponseSpec)
        whenever(mockResponseSpec.bodyToMono(Array<String>::class.java))
            .thenThrow(WebClientResponseException::class.java)

        val response = matchHistoryService.getMatchHistory(testPlayerId,testNumberOfMatches, testAPI) as MatchHistoryService.GetMatchHistoryResults.MatchesNotFound
        // Then
        assertEquals("Matches not Found", response.message)
    }

    /*
    @Test()
    fun testGetAccountInfo_ThrowsRuntimeException_WhenEncountersHttpServerErrorException(){
        //Given
        val testNumberOfMatches = 15
        val testPlayerId = "testPlayerId"
        val testMatchHistoryResponseDTO = getRandomMatchHistoryFromInt(numberOfMatches = testNumberOfMatches)

        //When
        whenever(mockWebClient.get())
            .thenReturn(mockRequestHeaderURI)
        whenever(mockRequestHeaderURI.uri(any<URI>()))
            .thenReturn(mockRequestHeadersSpec)
        whenever(mockRequestHeadersSpec.retrieve())
            .thenReturn(mockResponseSpec)
        whenever(mockResponseSpec.bodyToMono(MatchHistoryResponseDTO::class.java))
            .thenAnswer{throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error") }

        //Then
        assertFailsWith(
            exceptionClass = RuntimeException::class,
            message = "Server Error",
            block = {matchHistoryService.getMatchHistory(testPlayerId,testNumberOfMatches)}
        )
    }

     */
}