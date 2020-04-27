package com.example.android.urbandictionary.single

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.urbandictionary.R

class FragmentDefinitionItem : Fragment() {

    companion object {
        fun newInstance() = FragmentDefinitionItem()
    }

    private lateinit var viewModel: ViewModelDefinitionItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_definition_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewModelDefinitionItem::class.java)
        // TODO: Use the ViewModel
    }

}
