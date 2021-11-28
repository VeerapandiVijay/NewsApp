package com.hapmate.newssample.data.repository

import com.hapmate.newssample.data.api.ApiService
import com.hapmate.newssample.data.model.articles.ArticleResult
import com.hapmate.newssample.utils.Resource

class MainRepository(private val apiHelper: ApiService) {

    suspend fun getArticles(): Resource<ArticleResult> {
        return try {
            val response = apiHelper.getArticles()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occurred")
        }
    }

}