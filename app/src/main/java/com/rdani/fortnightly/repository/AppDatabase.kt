package com.rdani.fortnightly.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rdani.fortnightly.model.Article
import com.rdani.fortnightly.repository.db.ArticleDao
import com.rdani.fortnightly.utils.DateConverter

@Database(entities = [Article::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}