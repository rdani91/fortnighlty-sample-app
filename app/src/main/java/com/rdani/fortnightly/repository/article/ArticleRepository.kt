package com.rdani.fortnightly.repository.article

import com.rdani.fortnightly.model.Article

interface ArticleRepository {

    suspend fun loadArticles(forced:Boolean): List<Article>?
}