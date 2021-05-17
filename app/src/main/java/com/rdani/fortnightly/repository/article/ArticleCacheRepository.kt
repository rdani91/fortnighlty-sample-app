package com.rdani.fortnightly.repository.article

import com.rdani.fortnightly.model.Article

interface ArticleCacheRepository {

    fun getArticles(): List<Article>

    fun saveArticles(articles: List<Article>)
}