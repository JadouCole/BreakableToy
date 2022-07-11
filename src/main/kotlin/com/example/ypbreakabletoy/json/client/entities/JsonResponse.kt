package com.example.ypbreakabletoy.json.client.entities

public data class JsonResponse<T>(
    val status: String,
    val data: T? = null,
    val message: String? = null,
    val errorCode: Int? = null
)