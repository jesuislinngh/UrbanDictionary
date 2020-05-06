package com.example.android.urbandictionary.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "definitions_table")
data class DefinitionEntity (@PrimaryKey @ColumnInfo(name = "word") val word: String,
                             val definition: String,
                             val permalink: String,
                             val thumbs_up: String = "0",
                             val sound_urls: String,
                             val author: String,
                             val defid: Int,
                             val current_vote: String,
                             val written_on: String,
                             val example: String,
                             val thumbs_down: String = "0") : Parcelable

fun DefinitionEntity.toDefinitionItem() = DefinitionItem(
    word = word,
    definition = definition,
    permalink = permalink,
    thumbs_up = thumbs_up,
    sound_urls = listOf(sound_urls),
    author = author,
    defid = defid,
    current_vote = current_vote,
    written_on = written_on,
    example = example,
    thumbs_down = thumbs_down)

@Parcelize
data class DefinitionList(
    val list: List<DefinitionItem>
) : Parcelable

@Parcelize
data class DefinitionItem(
    val definition: String,
    val permalink: String,
    val thumbs_up: String = "0",
    val sound_urls: List<String>,
    val author: String,
    val word: String,
    val defid: Int,
    val current_vote: String,
    val written_on: String,
    val example: String,
    val thumbs_down: String = "0"
) : Parcelable