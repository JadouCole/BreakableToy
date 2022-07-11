package com.example.ypbreakabletoy.json.entities

import com.example.ypbreakabletoy.json.constants.JsonResponseStatus
import com.example.ypbreakabletoy.json.entities.interfaces.JsonSuccessResponse

internal class JsonSuccessResponseImpl<T>() : JsonSuccessResponse<T> {

    constructor(data: T) : this() {
        this.data = data
    }
    public override val status: String = JsonResponseStatus.SUCCESS.toString()
    public override var data: T? = null
        private set
}