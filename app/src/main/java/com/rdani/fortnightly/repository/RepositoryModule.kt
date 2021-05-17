package com.rdani.fortnightly.repository

import com.rdani.fortnightly.repository.article.ArticleCacheRepository
import com.rdani.fortnightly.repository.article.ArticleRepository
import com.rdani.fortnightly.repository.article.DefaultArticleRepository
import com.rdani.fortnightly.repository.article.PersistentArticleCacheRepository
import org.koin.dsl.module

object RepositoryModule {

    val module = module {
        single<ArticleCacheRepository> { PersistentArticleCacheRepository(get()) }
        single<ArticleRepository> { DefaultArticleRepository(get(), get()) }
    }
}