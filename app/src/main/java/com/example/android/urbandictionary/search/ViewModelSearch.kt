package com.example.android.urbandictionary.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.urbandictionary.data.NetworkRequestError
import com.example.android.urbandictionary.data.RepositoryDefinitions
import com.example.android.urbandictionary.utils.singleArgViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModelSearch(private val repository: RepositoryDefinitions) : ViewModel() {
    companion object {

        /**
         * Factory for creating [ViewModelSearch]
         *
         * @param repository the repository to pass to [ViewModelSearch]
         */
        val FACTORY = singleArgViewModelFactory(::ViewModelSearch)
    }

    private val noTermError: String = "Term not found, try another one"

    /**
     * Request a snackbar to display a string.
     */
    private val _snackBar = MutableLiveData<String>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String>
        get() = _snackBar

    private val _searching = MutableLiveData(false)

    /**
     * Show a loading progress bar if true and hide or disabled other visual elements.
     */
    val searching: LiveData<Boolean>
        get() = _searching

    fun getDefinition(term: String, resume: () -> Unit) {

        if (term.length < 2) {
            _snackBar.value = noTermError
        } else {
            startProcess { repository.getWordDefinition(term) }
            resume()
        }

    }

    private fun startProcess(block: suspend () -> Unit) : Job {
        return viewModelScope.launch {
            try {
                _searching.value = true
                block()
            } catch (error: NetworkRequestError) {
                _snackBar.value = error.localizedMessage
            } finally {
                _searching.value = false
            }
        }
    }
}
