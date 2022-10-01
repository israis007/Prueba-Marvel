package com.test.app.objects

import com.google.gson.annotations.SerializedName

data class ResultsPersons(
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("gender") val gender : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("known_for") val known_for : List<KnowFor>,
    @SerializedName("known_for_department") val known_for_department : String,
    @SerializedName("name") val name : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("profile_path") val profile_path : String
)
