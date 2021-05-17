package com.rdani.fortnightly.model

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
