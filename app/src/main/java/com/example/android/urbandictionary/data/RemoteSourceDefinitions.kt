package com.example.android.urbandictionary.data


interface RemoteSourceDefinitions {

    suspend fun getDefinitions(term: String) : List<DefinitionItem>
}