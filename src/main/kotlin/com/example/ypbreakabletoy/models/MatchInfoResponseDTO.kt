package com.example.ypbreakabletoy.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


@Suppress("UNCHECKED_CAST")
private fun <T> ObjectMapper.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonNode) -> T, toJson: (T) -> String, isUnion: Boolean = false) = registerModule(SimpleModule().apply {
    addSerializer(k.java as Class<T>, object : StdSerializer<T>(k.java as Class<T>) {
        override fun serialize(value: T, gen: JsonGenerator, provider: SerializerProvider) = gen.writeRawValue(toJson(value))
    })
    addDeserializer(k.java as Class<T>, object : StdDeserializer<T>(k.java as Class<T>) {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext) = fromJson(p.readValueAsTree())
    })
})

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    //convert(Name::class, { Name.fromValue(it.asText()) }, { "\"${it.value}\"" })
}

data class MatchInfoResponseDTO (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val metadata: Metadata,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val info: Info
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MatchInfoResponseDTO>(json)
    }
}

data class Info (
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

data class Participant (
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

data class Companion (
    @get:JsonProperty("content_ID", required=true)@field:JsonProperty("content_ID", required=true)
    val contentID: String,

    @get:JsonProperty("skin_ID", required=true)@field:JsonProperty("skin_ID", required=true)
    val skinID: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val species: String
)

data class Trait (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val name: String,

    @get:JsonProperty("num_units", required=true)@field:JsonProperty("num_units", required=true)
    val numUnits: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val style: Long,

    @get:JsonProperty("tier_current", required=true)@field:JsonProperty("tier_current", required=true)
    val tierCurrent: Long,

    @get:JsonProperty("tier_total", required=true)@field:JsonProperty("tier_total", required=true)
    val tierTotal: Long
)

data class Unit (
    @get:JsonProperty("character_id", required=true)@field:JsonProperty("character_id", required=true)
    val characterID: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val items: List<Long>,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val name: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val rarity: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val tier: Long
)
/*
enum class Name(val value: String) {
    Draven("Draven"),
    Empty(""),
    Khazix("Khazix"),
    Lucian("Lucian"),
    RekSai("RekSai");

    companion object {
        fun fromValue(value: String): Name = when (value) {
            "Draven" -> Draven
            ""       -> Empty
            "Khazix" -> Khazix
            "Lucian" -> Lucian
            "RekSai" -> RekSai
            else     -> throw IllegalArgumentException()
        }
    }
}
*/
data class Metadata (
    @get:JsonProperty("data_version", required=true)@field:JsonProperty("data_version", required=true)
    val dataVersion: String,

    @get:JsonProperty("match_id", required=true)@field:JsonProperty("match_id", required=true)
    val matchID: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val participants: List<String>
)
