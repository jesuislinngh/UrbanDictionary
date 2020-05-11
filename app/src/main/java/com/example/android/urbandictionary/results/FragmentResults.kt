package com.example.android.urbandictionary.results

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.urbandictionary.R

import com.example.android.urbandictionary.databinding.FragmentResultsBinding
import com.example.android.urbandictionary.search.ViewModelSearch

class FragmentResults : Fragment() {

    private val TAG =  FragmentResults::class.java.canonicalName

    private lateinit var viewModel: ViewModelSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.definitionList.adapter =
            AdapterDefinitionResults(AdapterDefinitionResults.OnClickListener {
                viewModel.setDefinition(it.defid)
                findNavController().navigate(R.id.fragmentDefinitionItem)
            })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_vote, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.filterThumbsUp -> {
            viewModel.orderTermsByThumbs(true)
            true
        }

        R.id.filterThumbsDown -> {
            viewModel.orderTermsByThumbs(false)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
