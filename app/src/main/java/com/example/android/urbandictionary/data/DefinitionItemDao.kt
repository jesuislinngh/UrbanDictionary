package com.example.android.urbandictionary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DefinitionItemDao {
    @Query("Select * from definitions_table ORDER BY word ASC")
    fun getDefinitions(): LiveData<List<DefinitionItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(definitionItem: DefinitionItem)

    @Query("DELETE FROM definitions_table")
    suspend fun deleteAll()
}