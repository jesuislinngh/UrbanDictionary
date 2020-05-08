package com.example.android.urbandictionary.search

import android.app.Application
import androidx.lifecycle.*
import com.example.android.urbandictionary.data.*
import com.example.android.urbandictionary.utils.singleArgViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/*
* This ViewModel is intended to be shared between several screens
* */
class ViewModelSearch(application: Application) : AndroidViewModel(application) {

    private val TAG = ViewModelSearch::class.java.canonicalName

    private var repository: RepositoryDefinitions

    // Create a Coroutine scope using a job to be able to cancel when needed
    //private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    //private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    companion object {

        /**
         * Factory for creating [ViewModelSearch]
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

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _definitions = MutableLiveData<List<DefinitionItem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val definitions: LiveData<List<DefinitionItem>>
        get() = _definitions

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _term = MutableLiveData<String>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val term: LiveData<String>
        get() = _term

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _definition = MutableLiveData<DefinitionItem>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val definition: LiveData<DefinitionItem>
        get() = _definition

    fun setDefinition(defid: Int) {
        _definition.value = _definitions.value?.first {
            it.defid == defid
        }
    }

    fun getDefinitions(term: String, resume: () -> Unit) {
        if (term.length < 2) {
            _snackBar.value = noTermError
        } else {
            startProcess {
                _definitions.value = repository.getTermDefinitions(term)

                if (_definitions.value?.isEmpty()!!) _snackBar.value = noTermError else {

                    _term.value = term
                    resume()
                }
            }
        }
    }

    // this is a repetitive task
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
