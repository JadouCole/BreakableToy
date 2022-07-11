package com.example.ypbreakabletoy.json.entities

import com.example.ypbreakabletoy.json.constants.JsonResponseStatus
import com.example.ypbreakabletoy.json.entities.interfaces.JsonErrorResponse


internal class JsonErrorResponseImpl(
    public override val data: Map<String, String>?,
    public override val message: String?,
    public override val errorCode: Int?) : JsonErrorResponse {
    public override val status: String = JsonResponseStatus.ERROR.toString()
}