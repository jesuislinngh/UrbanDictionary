package com.example.android.urbandictionary.results

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.urbandictionary.R

class FragmentResults : Fragment() {

    companion object {
        fun newInstance() = FragmentResults()
    }

    private lateinit var viewModelResults: ViewModelResults

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_results_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelResults = ViewModelProviders.of(this).get(ViewModelResults::class.java)
        // TODO: Use the ViewModel
    }

}
