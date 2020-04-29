package com.example.android.urbandictionary.results

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.urbandictionary.databinding.FragmentResultsBinding

class FragmentResults : Fragment() {

    private val TAG =  FragmentResults::class.java.canonicalName

    private val viewModel: ViewModelResults by lazy {
        ViewModelProviders.of(this).get(ViewModelResults::class.java)
    }

    companion object {
        fun newInstance() = FragmentResults()
    }

    private lateinit var viewModelResults: ViewModelResults

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelResults = ViewModelProviders.of(this).get(ViewModelResults::class.java)
        // TODO: Use the ViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentResultsBinding.inflate(inflater, container, false)
        binding.definitionList.adapter =
            AdapterDefinitionResults(AdapterDefinitionResults.OnClickListener {
            Log.d(TAG, "We got here...")
        })

        return binding.root
    }
}
