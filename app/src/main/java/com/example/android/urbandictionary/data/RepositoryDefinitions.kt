package com.example.android.urbandictionary.data

import android.util.Log

class RepositoryDefinitions(private val daoDefinitions: DaoDefinitionItem) {

    private val TAG = RepositoryDefinitions::class.java.canonicalName

    suspend fun getWordDefinition(term: String) {
        // interact with *blocking* network and IO calls from a coroutine
        try {
            // Make network request using a blocking call
            /*val result =  withTimeout(5_000) {
                network.fetchNextTitle()
            } */
            //titleDao.insertTitle(Title(result.toString()))
            Log.d(TAG, "we got here, simulating a network request...")
        } catch (cause: Throwable) {
            // If the network throws an exception, inform the caller
            throw NetworkRequestError("Unable to refresh title", cause)
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