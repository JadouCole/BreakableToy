package com.example.ypbreakabletoy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Participantt (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val augments: List<String>,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val companion: Companion,

    @get:JsonProperty("gold_left", required=true)@field:JsonProperty("gold_left", required=true)
    val goldLeft: Long,

    @get:JsonProperty("last_round", required=true)@field:JsonProperty("last_round", required=true)
    val lastRound: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val level: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val placement: Long,

    @get:JsonProperty("players_eliminated", required=true)@field:JsonProperty("players_eliminated", required=true)
    val playersEliminated: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val puuid: String,

    @get:JsonProperty("time_eliminated", required=true)@field:JsonProperty("time_eliminated", required=true)
    val timeEliminated: Double,

    @get:JsonProperty("total_damage_to_players", required=true)@field:JsonProperty("total_damage_to_players", required=true)
    val totalDamageToPlayers: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val traits: List<Trait>,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val units: List<Unit>
)