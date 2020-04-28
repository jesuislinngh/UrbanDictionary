package com.example.android.urbandictionary.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.urbandictionary.R

class FragmentSearch : Fragment() {

    companion object {
        fun newInstance() = FragmentSearch()
    }

    private lateinit var viewModelSearch: ViewModelSearch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelSearch = ViewModelProviders.of(this).get(ViewModelSearch::class.java)
        // TODO: Use the ViewModel
    }

}
