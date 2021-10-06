package com.test.app.rest.state

data class Resource<T>(
    var statusType      : StatusType,
    var data            : T?                = null,
    var title           : String?           = null,
    var message         : String?           = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(StatusType.SUCCESS, data = data)

        fun <T> loading(): Resource<T> = Resource(StatusType.LOADING)

        fun <T> error(title: String, message: String, data: T? = null): Resource<T> =
            Resource(StatusType.ERROR, message = message, data = data, title = title)
    }
}