package com.example.ypbreakabletoy.json.entities.interfaces

public interface JsonFailureResponse : JsonResponse {
    public val data: Map<String, String>?
    public val message: String?
    public val errorCode: Int?
}