package com.example.ypbreakabletoy.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tft-set6")
data class CharacterModel(
    @Id
    val mongoId: ObjectId,

    val champion: String,
    val trait_0: String?,
    val trait_1: String?

) {
}