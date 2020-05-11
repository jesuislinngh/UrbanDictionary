package com.example.android.urbandictionary.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "definitions_table")
data class DefinitionEntity (val word: String,
                             val definition: String,
                             val permalink: String,
                             val thumbs_up: String = "0",
                             val sound_urls: String,
                             val author: String,
                             @PrimaryKey @ColumnInfo(name = "defid") val defid: Int,
                             val current_vote: String,
                             val written_on: String,
                             val example: String,
                             val thumbs_down: String = "0") : Parcelable {
    companion object {
        private fun toDefinitionItem(entity: DefinitionEntity) = DefinitionItem(
            word = entity.word,
            definition = entity.definition,
            permalink = entity.permalink,
            thumbs_up = entity.thumbs_up,
            sound_urls = entity.sound_urls.split(","),
            author = entity.author,
            defid = entity.defid,
            current_vote = entity.current_vote,
            written_on = entity.written_on,
            example = entity.example,
            thumbs_down = entity.thumbs_down)

        fun convertToDefinitionItemList(list: List<DefinitionEntity>) : List<DefinitionItem> {
            val definitionItem = mutableListOf<DefinitionItem>()

            list.forEach {
                definitionItem.add(toDefinitionItem(it))
            }

            return definitionItem
        }
    }
}





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
) : Parcelable {
    companion object {
        private fun toDefinitionEntity(item: DefinitionItem) = DefinitionEntity(
            word = item.word,
            definition = item.definition,
            permalink = item.permalink,
            thumbs_up = item.thumbs_up,
            sound_urls = item.sound_urls.toString(),
            author = item.author,
            defid = item.defid,
            current_vote = item.current_vote,
            written_on = item.written_on,
            example = item.example,
            thumbs_down = item.thumbs_down)

        fun convertToDefinitionEntityList(list: List<DefinitionItem>) : List<DefinitionEntity> {
            val definitionEntity = mutableListOf<DefinitionEntity>()

            list.forEach {
                definitionEntity.add(toDefinitionEntity(it))
            }

            return definitionEntity
        }
    }
}