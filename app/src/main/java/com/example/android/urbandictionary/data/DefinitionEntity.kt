package com.example.android.urbandictionary.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefinitionList(
    val list: List<DefinitionItem>
) : Parcelable

@Parcelize
@Entity(tableName = "definitions_table")
data class DefinitionItem (@PrimaryKey @ColumnInfo(name = "word") val word: String,
                             val definition: String,
                             val permalink: String,
                             val thumbs_up: String = "0",
                             val sound_urls: List<String>,
                             val author: String,
                             val defid: Int,
                             val current_vote: String,
                             val written_on: String,
                             val example: String,
                             val thumbs_down: String = "0") : Parcelable