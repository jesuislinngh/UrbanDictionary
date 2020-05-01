package com.example.android.urbandictionary.search

import android.app.Application
import androidx.lifecycle.*
import com.example.android.urbandictionary.data.DataBaseDefinitions
import com.example.android.urbandictionary.data.NetworkRequestError
import com.example.android.urbandictionary.data.RepositoryDefinitions
import com.example.android.urbandictionary.utils.singleArgViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/*
* This ViewModel is intended to be shared between several screens
* */
class ViewModelSearch(application: Application) : AndroidViewModel(application) {

    private var repository: RepositoryDefinitions

    companion object {

        /**
         * Factory for creating [ViewModelSearch]
         *
         * @param repository the repository to pass to [ViewModelSearch]
         */
        val FACTORY = singleArgViewModelFactory(::ViewModelSearch)
    }


    init {
        val daoDefinition = DataBaseDefinitions.getDatabase(application, viewModelScope).daoDefinition()
        repository = RepositoryDefinitions(daoDefinition)
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

    fun getDefinitions(term: String, resume: () -> Unit) {

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
