package com.example.android.urbandictionary.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.urbandictionary.data.DefinitionItem

class ViewModelResults : ViewModel() {
    // TODO: Implement the ViewModel
    private val _definitions = MutableLiveData<List<DefinitionItem>>()

    val definitions: LiveData<List<DefinitionItem>>
        get() = _definitions
}
