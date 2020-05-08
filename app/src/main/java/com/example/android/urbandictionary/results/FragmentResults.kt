package com.example.android.urbandictionary.results

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android.urbandictionary.R

import com.example.android.urbandictionary.databinding.FragmentResultsBinding
import com.example.android.urbandictionary.search.ViewModelSearch

class FragmentResults : Fragment() {

    private val TAG =  FragmentResults::class.java.canonicalName

    private lateinit var viewModel: ViewModelSearch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders
            .of(
                requireActivity(),
                ViewModelSearch.FACTORY(requireContext().applicationContext as Application)
            )
            .get(ViewModelSearch::class.java)

        val binding = FragmentResultsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.definitionList.adapter =
            AdapterDefinitionResults(AdapterDefinitionResults.OnClickListener {
                viewModel.setDefinition(it.defid)
                findNavController().navigate(R.id.fragmentDefinitionItem)
            })

        return binding.root
    }
}
