package com.example.android.urbandictionary.data

import androidx.lifecycle.LiveData

interface LocalSourceDefinitions {
    suspend fun getDefinitions(): LiveData<List<DefinitionItem>>

    suspend fun getDefinition(): LiveData<List<DefinitionItem>>

    suspend fun insertAll(definitions: List<DefinitionItem>)

    suspend fun insert(definition: DefinitionItem)

    suspend fun deleteAll()
}