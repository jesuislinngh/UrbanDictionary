package com.example.android.urbandictionary.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * This type converter code was found at https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9
 * Article written by Todd Cooke https://toddcooke.github.io
 * **/


class GithubTypeConverters {

    companion object {
        var gson: Gson = Gson()

        @TypeConverter
        fun stringToSomeObjectList(data: String?): List<String> {
            if (data == null) {
                return Collections.emptyList()
            }
            val listType: Type =
                object : TypeToken<List<String?>?>() {}.getType()
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObjects: List<String?>?): String {
            return gson.toJson(someObjects)
        }
    }
}