package com.example.ypbreakabletoy.json.constants

public enum class JsonResponseStatus {

    SUCCESS,
    FAILURE,
    ERROR;

    public override fun toString(): String {
        return name.lowercase()
    }
}