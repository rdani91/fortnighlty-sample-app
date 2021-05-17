package com.rdani.fortnightly.api.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.text.SimpleDateFormat
import java.util.*

class CustomDateAdapter : JsonAdapter<Date>() {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    }

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    override fun fromJson(reader: JsonReader): Date? {

        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }
}