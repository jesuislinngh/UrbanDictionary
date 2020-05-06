package com.example.android.urbandictionary.data

import android.util.Log
import androidx.lifecycle.LiveData


class RepositoryDefinitions(private val daoDefinitions: DaoDefinitionItem) : RemoteSourceDefinitions {

    private val TAG = RepositoryDefinitions::class.java.canonicalName

    suspend fun getTermDefinitions(term: String): List<DefinitionItem>? {

        val definitions: LiveData<List<DefinitionEntity>>? = daoDefinitions.getDefinitions(term)

        return if (definitions == null || definitions.value == null) {
            getDefinitions(term)
        } else {
            val definitionItems = mutableListOf<DefinitionItem>()

            definitions.value?.forEach {
                definitionItems.add(it.toDefinitionItem())
            }

            definitionItems
        }

    }

    override suspend fun getDefinitions(term: String) : List<DefinitionItem> {
        val getTermsDeferred = DictionaryApi.retrofitService.getTermAsync(term)
        return try {
            // this will run on a thread managed by Retrofit
            val listResult = getTermsDeferred.await()
            Log.d(TAG, "we got a list from the server: ${listResult.list.size}")

            listResult.list

        } catch (e: Exception) {
            Log.d(TAG, "Stacktrace", e)
            emptyList()
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