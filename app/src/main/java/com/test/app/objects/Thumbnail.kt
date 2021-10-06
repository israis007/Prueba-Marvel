package com.test.app.objects

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path")         val path        : String,
    @SerializedName("extension")    val extension   : String
)
