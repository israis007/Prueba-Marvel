package com.test.app.rest.responses

import com.google.gson.annotations.SerializedName
import com.test.app.objects.Data

data class CharactersResponse (
    @SerializedName("code")                 val code                : Int,
    @SerializedName("status")               val status              : String,
    @SerializedName("copyright")            val copyright           : String,
    @SerializedName("attributionText")      val attributionText     : String,
    @SerializedName("attributionHTML")      val attributionHTML     : String,
    @SerializedName("etag")                 val etag                : String,
    @SerializedName("data")                 val data                : Data
    ){

}