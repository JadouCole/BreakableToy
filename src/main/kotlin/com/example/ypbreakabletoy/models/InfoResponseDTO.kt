package com.example.ypbreakabletoy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Infot (
    @get:JsonProperty("game_datetime", required=true)@field:JsonProperty("game_datetime", required=true)
    val gameDatetime: Long,

    @get:JsonProperty("game_length", required=true)@field:JsonProperty("game_length", required=true)
    val gameLength: Double,

    @get:JsonProperty("game_version", required=true)@field:JsonProperty("game_version", required=true)
    val gameVersion: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val participants: List<Participant>,

    @get:JsonProperty("queue_id", required=true)@field:JsonProperty("queue_id", required=true)
    val queueID: Long,

    @get:JsonProperty("tft_game_type", required=true)@field:JsonProperty("tft_game_type", required=true)
    val tftGameType: String,

    @get:JsonProperty("tft_set_number", required=true)@field:JsonProperty("tft_set_number", required=true)
    val tftSetNumber: Long
)