package com.rdani.fortnightly.api

import com.rdani.fortnightly.api.adapters.CustomDateAdapter
import com.rdani.fortnightly.api.service.ArticleService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object ApiModule {

    val module = module {
        single<OkHttpClient> {
            OkHttpClient
                .Builder()
                .addInterceptor(HeaderInterceptor())
                .build()
        }

        val moshi = Moshi
            .Builder()
            .add(Date::class.java, CustomDateAdapter())
            .build()

        single<Retrofit> {
            Retrofit
                .Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        single<ArticleService> {
            get<Retrofit>().create(ArticleService::class.java)
        }

    }
}