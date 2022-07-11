package com.example.ypbreakabletoy.services

import com.example.ypbreakabletoy.config.AccountAPIConfig
import com.example.ypbreakabletoy.models.AccountInfoResponseDTO
import com.example.ypbreakabletoy.testdata.getRandomAccountDTO
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import java.net.URI
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class AccountInfoServiceTest {
    private val mockWebClient: WebClient = mock ()
    private val mockAccountAPIConfig: AccountAPIConfig = mock ()
    private val mockRequestHeaderURI: WebClient.RequestHeadersUriSpec<*> = mock ()
    private val mockRequestHeadersSpec: WebClient.RequestHeadersSpec<*> = mock ()
    private val mockResponseSpec: ResponseSpec = mock ()

    private var accountInfoService: AccountInfoService = AccountInfoService(mockAccountAPIConfig, mockWebClient)

    @Before
    fun setup(){
        whenever(mockAccountAPIConfig.baseUrl).thenReturn("this needs to be changed")
        whenever(mockAccountAPIConfig.getAccount).thenReturn("this also needs to be changed")
    }

    @Test
    fun testGetAccountInfo_HappyPath(){
        //Given
        val riotName = "testAcc"
        val riotTag = "testTag"
        val apikey = "testAPI"
        val mockAccountDTO : AccountInfoResponseDTO = getRandomAccountDTO(gameName = riotName, tagLine = riotTag)

        //When
        whenever(mockWebClient.get())
            .thenReturn(mockRequestHeaderURI)
        whenever(mockRequestHeaderURI.uri(any<URI>()))
            .thenReturn(mockRequestHeadersSpec)
        whenever(mockRequestHeadersSpec.retrieve())
            .thenReturn(mockResponseSpec)
        whenever(mockResponseSpec.bodyToMono(AccountInfoResponseDTO::class.java))
            .thenReturn(Mono.just(mockAccountDTO))

        val response = accountInfoService.getAccountInfo(riotName,riotTag, apikey ) as AccountInfoService.GetAccountInfoResults.Success

        //Then
        assertEquals(mockAccountDTO.gameName, response.accountInfo.block()?.gameName)
        assertEquals(mockAccountDTO.tagLine, response.accountInfo.block()?.tagLine)
    }

    @Test
    fun testGetAccountInfo_ReturnsNull_WhenAccountNotFound(){
        // Given
        val riotName = ""
        val riotTag = ""
        val apikey = ""

        // When
        whenever(mockWebClient.get())
            .thenReturn(mockRequestHeaderURI)
        whenever(mockRequestHeaderURI.uri(any<URI>()))
            .thenReturn(mockRequestHeadersSpec)
        whenever(mockRequestHeadersSpec.retrieve())
            .thenReturn(mockResponseSpec)
        whenever(mockResponseSpec.bodyToMono(AccountInfoResponseDTO::class.java))
            .thenThrow(WebClientResponseException::class.java)

        val response = accountInfoService.getAccountInfo(riotName, riotTag, apikey) as AccountInfoService.GetAccountInfoResults.AccountNotFound

        // Then
        assertEquals("Account not Found", response.message)
    }

    @Test()
    fun testGetAccountInfo_ThrowsRuntimeException_WhenEncountersHttpServerErrorException(){
    }

    @Test
    fun getAccountInfo_ThrowsRuntimeException_WhenEncountersOtherHttpStatusCodeException(){
    }

}