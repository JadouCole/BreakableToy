package com.example.ypbreakabletoy.services

import com.example.ypbreakabletoy.config.AccountAPIConfig
import com.example.ypbreakabletoy.testdata.getRandomAccountDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient

@RunWith(SpringRunner::class)
class AccountInfoServiceIntegrationTest {

    private val mockAccountAPIConfig: AccountAPIConfig = mock ()
    private val mockWebServer: MockWebServer = MockWebServer()

    private val mockAccountService = AccountInfoService(mockAccountAPIConfig, WebClient.create())

    @Before
    fun setup(){
        mockWebServer.start()

    }
    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
    @BeforeEach
    fun initialize(){

    }
    @Test
    fun testGetAccountInfo_HappyPath_IntegrationTest(){
        val testName = "Jadocole"
        val testTag = "Doug"
        val testAPI = "testAPI"
        val testAccountInfoResponseDTO = getRandomAccountDTO(gameName = testName, tagLine = testTag)

        mockWebServer.enqueue(MockResponse()
            .setBody(jacksonObjectMapper().writeValueAsString(testAccountInfoResponseDTO)))

        mockAccountService.getAccountInfo(testName,testTag, testAPI)
        val response: RecordedRequest = mockWebServer.takeRequest()

        assert(response.method.equals("GET"))
        assert(response.path.equals("\"https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/${testName}/${testTag}?api_key=\\\${api.key}\""))
    }
}