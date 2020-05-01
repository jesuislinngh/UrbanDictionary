package com.example.android.urbandictionary.data

import androidx.lifecycle.LiveData

interface LocalSourceDefinitions {
    suspend fun getDaoDefinitions(): LiveData<List<DefinitionItem>>

    suspend fun getDaoDefinition(): LiveData<List<DefinitionItem>>

    suspend fun insertAll(definitions: List<DefinitionItem>)

    suspend fun insert(definition: DefinitionItem)

    suspend fun deleteAll()
}