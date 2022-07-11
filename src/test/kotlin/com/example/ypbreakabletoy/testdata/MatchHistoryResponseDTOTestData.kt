package com.example.ypbreakabletoy.testdata

import com.example.ypbreakabletoy.models.MatchHistoryResponseDTO
import com.github.javafaker.Faker

fun getRandomMatchHistoryFromInt(
    numberOfMatches: Int,
    listOfMatches: List<String> = (1..numberOfMatches).map{ Faker().lorem().word()}
): MatchHistoryResponseDTO{
    return MatchHistoryResponseDTO(
        matchHistory = listOfMatches
    )
}