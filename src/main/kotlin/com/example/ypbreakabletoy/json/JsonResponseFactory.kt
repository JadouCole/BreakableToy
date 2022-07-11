package com.example.ypbreakabletoy.json

import com.example.ypbreakabletoy.json.entities.JsonErrorResponseImpl
import com.example.ypbreakabletoy.json.entities.JsonFailureResponseImpl
import com.example.ypbreakabletoy.json.entities.JsonSuccessResponseImpl
import com.example.ypbreakabletoy.json.entities.interfaces.JsonErrorResponse
import com.example.ypbreakabletoy.json.entities.interfaces.JsonFailureResponse
import com.example.ypbreakabletoy.json.entities.interfaces.JsonSuccessResponse

public fun success(): JsonSuccessResponse<Any?> {
    return JsonSuccessResponseImpl(data = null as Any?)
}

public fun <T> success(data: T): JsonSuccessResponse<T> {
    return JsonSuccessResponseImpl(data = data)
}

@JvmOverloads
public fun failure(data: Map<String, String>, message: String, errorCode: Int? = null): JsonFailureResponse {
    return JsonFailureResponseImpl(data = data, message = message, errorCode = errorCode)
}

@JvmOverloads
public fun failure(data: Map<String, String>, errorCode: Int? = null): JsonFailureResponse {
    return JsonFailureResponseImpl(data = data, message = null, errorCode = errorCode)
}

@JvmOverloads
public fun failure(message: String, errorCode: Int? = null): JsonFailureResponse {
    return JsonFailureResponseImpl(data = null, message = message, errorCode = errorCode)
}

@JvmOverloads
public fun error(message: String, data: Map<String, String>? = null, errorCode: Int? = null): JsonErrorResponse {
    return JsonErrorResponseImpl(message = message, data = data, errorCode = errorCode)
}