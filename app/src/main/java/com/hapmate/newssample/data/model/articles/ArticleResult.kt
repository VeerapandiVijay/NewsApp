package com.hapmate.newssample.data.model.articles

import com.google.gson.annotations.SerializedName

data class ArticleResult(
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int,
    @SerializedName("articles")
    val articles: MutableList<Articles>,
)
