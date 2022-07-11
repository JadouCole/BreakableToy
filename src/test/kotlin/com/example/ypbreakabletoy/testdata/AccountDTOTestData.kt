package com.example.ypbreakabletoy.testdata


import com.example.ypbreakabletoy.models.AccountInfoResponseDTO
import com.github.javafaker.Faker


fun getRandomAccountDTO(
    puuid: String = Faker().number().digits(4),
    gameName: String = Faker().lorem().word(),
    tagLine: String = Faker().lorem().word()
):AccountInfoResponseDTO{
    return AccountInfoResponseDTO(
        puuid = puuid,
        gameName = gameName,
        tagLine = tagLine
    )
}