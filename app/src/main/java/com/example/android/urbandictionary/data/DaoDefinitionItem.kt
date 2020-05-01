package com.example.android.urbandictionary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoDefinitionItem {
    @Query("SELECT * FROM definitions_table WHERE word = :word ORDER BY word ASC")
    suspend fun getDefinitions(word: String): LiveData<List<DefinitionItem>>

    @Query("SELECT * FROM definitions_table WHERE word = :word AND defid = :defid ORDER BY word ASC")
    suspend fun getDefinition(word: String, defid: Int): LiveData<List<DefinitionItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(definitionItem: DefinitionItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(definitions: List<DefinitionItem>)

    @Query("DELETE FROM definitions_table")
    suspend fun deleteAll()
}