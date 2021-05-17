package com.rdani.fortnightly.repository.article

import android.content.Context
import androidx.room.Room
import com.rdani.fortnightly.model.Article
import com.rdani.fortnightly.repository.AppDatabase

class PersistentArticleCacheRepository(private val context: Context) : ArticleCacheRepository {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "fortnightly.db")
            .build()
    }

    override fun getArticles(): List<Article> {
        return db.articleDao().getAll()
    }

    override fun saveArticles(articles: List<Article>) {
        db.articleDao().clearAll()
        db.articleDao().insertAll(articles)
    }
}