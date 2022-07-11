package com.example.ypbreakabletoy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Companiont (
    @get:JsonProperty("content_ID", required=true)@field:JsonProperty("content_ID", required=true)
    val contentID: String,

    @get:JsonProperty("skin_ID", required=true)@field:JsonProperty("skin_ID", required=true)
    val skinID: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val species: String
)