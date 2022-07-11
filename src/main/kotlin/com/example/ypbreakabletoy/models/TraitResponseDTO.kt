package com.example.ypbreakabletoy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Traitt (
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