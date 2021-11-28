package com.hapmate.newssample.data.api


import com.hapmate.newssample.data.model.articles.ArticleResult
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("everything?q=android&sortBy=publishedAt&apiKey=d1129ee7cc7f409eb324c6228ce11726")
    suspend fun getArticles(): Response<ArticleResult>

}