package com.example.ypbreakabletoy.repositories

import com.example.ypbreakabletoy.models.CharacterModel
import org.springframework.data.mongodb.repository.MongoRepository

interface CharacterRepository :MongoRepository<CharacterModel,String> {
    fun findCharacterModelByChampion(champion:String): CharacterModel
}