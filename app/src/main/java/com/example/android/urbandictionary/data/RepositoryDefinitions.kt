package com.example.android.urbandictionary.data

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RepositoryDefinitions(private val daoDefinitions: DaoDefinitionItem) : RemoteSourceDefinitions {

    private val TAG = RepositoryDefinitions::class.java.canonicalName

    suspend fun getTermDefinitions(term: String): List<DefinitionItem>? {

        val definitions: LiveData<List<DefinitionEntity>>? = daoDefinitions.getDefinitions(term)

        return if (definitions == null || definitions.value == null) {
            val definitionItems =  getDefinitions(term)
            insertDefinitions(DefinitionItem.convertToDefinitionEntityList(definitionItems))
            definitionItems
        } else {
            DefinitionEntity.convertToDefinitionItemList(definitions.value!!)
        }

    }

    override suspend fun getDefinitions(term: String) : List<DefinitionItem> {
        val getTermsDeferred = DictionaryApi.retrofitService.getTermAsync(term)
        return try {
            // this will run on a thread managed by Retrofit
            val listResult = getTermsDeferred.await()
            listResult.list

        } catch (e: Exception) {
            Log.d(TAG, "Stacktrace", e)
            emptyList()
        }
    }

    private suspend fun insertDefinitions(list: List<DefinitionEntity>) {
        try {
            withContext(Dispatchers.IO) {
                daoDefinitions.insertAll(list)
            }
        } catch (error: Exception) {
            throw Exception(error.localizedMessage)
        }
    }
}

/**
 * Thrown when there was a error fetching a new title
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class NetworkRequestError(message: String, cause: Throwable?) : Throwable(message, cause)