package com.rdani.fortnightly.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rdani.fortnightly.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>

    @Insert
    fun insertAll(articles: List<Article>)

    @Query("DELETE FROM articles")
    fun clearAll()
}