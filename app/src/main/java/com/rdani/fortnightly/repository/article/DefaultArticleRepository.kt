package com.rdani.fortnightly.repository.article

import com.rdani.fortnightly.api.service.ArticleService
import com.rdani.fortnightly.model.Article

class DefaultArticleRepository(
    private val articleService: ArticleService,
    private val articleCacheRepository: ArticleCacheRepository
) : ArticleRepository {

    private val cachedArticles: ArrayList<Article> = arrayListOf()

    override suspend fun loadArticles(forced: Boolean): List<Article>? {
        if (!forced && cachedArticles.isNotEmpty()) return cachedArticles

        if (!forced) {
            val persistentCachedArticles = articleCacheRepository.getArticles()
            if (persistentCachedArticles.isNotEmpty()) return persistentCachedArticles
        }

        val everything = articleService.getEverything()
        if (everything?.isSuccessful != true || everything.body()?.articles?.isNullOrEmpty() == true) return null

        val articles = everything.body()?.articles ?: arrayListOf()

        cachedArticles.clear()
        cachedArticles.addAll(articles)
        articleCacheRepository.saveArticles(articles)

        return articles
    }
}