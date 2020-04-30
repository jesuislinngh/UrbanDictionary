package com.example.android.urbandictionary.search

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.android.urbandictionary.R
import com.example.android.urbandictionary.data.RepositoryDefinitions
import com.google.android.material.snackbar.Snackbar

class FragmentSearch : Fragment() {

    private val TAG = FragmentSearch::class.java.canonicalName

    companion object {

        fun newInstance() = FragmentSearch()
    }

    private lateinit var viewModel: ViewModelSearch
    private lateinit var imm: InputMethodManager

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val repository = RepositoryDefinitions()
        viewModel = ViewModelProviders
            .of(this, ViewModelSearch.FACTORY(repository))
            .get(ViewModelSearch::class.java)

        view.findViewById<Button>(R.id.send).setOnClickListener { searchTerm(view) }

        val editText = view.findViewById<EditText>(R.id.editText)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val nameObserver = Observer<Boolean> { value ->
            value.let { searching ->
                editText.isEnabled = !searching
                progressBar.visibility = if (searching) View.VISIBLE else View.GONE
            }
        }

        viewModel.searching.observe(viewLifecycleOwner, nameObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun searchTerm(view: View) {
        val editText = getView()?.findViewById<EditText>(R.id.editText)
        val term = editText?.text.toString()

        imm.hideSoftInputFromWindow(editText?.windowToken, 0)

        if (term.length > 1) {
            Log.d(TAG, "we got here searching...")
            viewModel.getDefinition(term) {
                findNavController()?.navigate(R.id.fragmentResults)
            }

        } else {
            getView()?.let {
                Snackbar.make(it, getString(R.string.no_term), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
