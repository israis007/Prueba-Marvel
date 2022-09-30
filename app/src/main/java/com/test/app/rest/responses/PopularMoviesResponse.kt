package com.test.app.rest.responses

import com.google.gson.annotations.SerializedName
import com.test.app.objects.Results

data class PopularMoviesResponse(
    @SerializedName("page")                 val page                : Int,
    @SerializedName("results")              val results             : List<Results>,
    @SerializedName("total_pages")          val total_pages         : Int,
    @SerializedName("total_results")        val total_results       : Int
)
