package com.example.ypbreakabletoy.json.entities

import com.example.ypbreakabletoy.json.constants.JsonResponseStatus
import com.example.ypbreakabletoy.json.entities.interfaces.JsonFailureResponse

internal class JsonFailureResponseImpl(
    public override val data: Map<String, String>?,
    public override val message: String?,
    public override val errorCode: Int?) : JsonFailureResponse {
    public override val status: String = JsonResponseStatus.FAILURE.toString()
}