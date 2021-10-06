package com.test.app.rest.state

enum class StatusType {
    SUCCESS,
    ERROR,
    LOADING;

    fun isLoading() = this == LOADING
}