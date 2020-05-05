package com.example.android.urbandictionary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoDefinitionItem {
    @Query("SELECT * FROM definitions_table WHERE word LIKE :word ORDER BY word ASC")
    fun getDefinitions(word: String): LiveData<List<DefinitionEntity>>

    @Query("SELECT * FROM definitions_table WHERE word LIKE :word AND defid LIKE :defid ORDER BY word ASC")
    fun getDefinition(word: String, defid: Int): LiveData<List<DefinitionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(term: DefinitionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(definitions: List<DefinitionEntity>)

    @Query("DELETE FROM definitions_table")
    fun deleteAll()
}