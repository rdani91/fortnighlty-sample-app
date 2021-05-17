package com.rdani.fortnightly.api.service

import com.rdani.fortnightly.model.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ArticleService {

    @Headers("Content-Type:application/json")
    @GET("everything?q=android")
    suspend fun getEverything(): Response<ArticlesResponse?>?
}