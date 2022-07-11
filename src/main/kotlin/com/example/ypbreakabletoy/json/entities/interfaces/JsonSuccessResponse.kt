package com.example.ypbreakabletoy.json.entities.interfaces

public interface JsonSuccessResponse<T> : JsonResponse {
    public val data: T?
}