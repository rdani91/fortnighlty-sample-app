package com.rdani.fortnightly.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rdani.fortnightly.utils.DateConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,

    @TypeConverters(DateConverter::class)
    @ColumnInfo(name = "published_at") val publishedAt: Date?,

    @ColumnInfo(name = "content") val content: String
) : Serializable
