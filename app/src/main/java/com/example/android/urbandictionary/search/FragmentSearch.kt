package com.example.android.urbandictionary.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar

import com.example.android.urbandictionary.R

class FragmentSearch : Fragment() {

    private val TAG =  FragmentSearch::class.java.canonicalName

    companion object {

        fun newInstance() = FragmentSearch()
    }

    private lateinit var viewModelSearch: ViewModelSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelSearch = ViewModelProviders.of(this).get(ViewModelSearch::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.send).setOnClickListener { searchTerm(view) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun searchTerm(view: View) {
        Log.d(TAG, "we got here searching...")

        getView()?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE
        getView()?.findViewById<EditText>(R.id.editText)?.isEnabled = false
    }
}
