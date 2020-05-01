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
    private lateinit var editText: EditText
    private var menu: Menu? = null

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

        editText = view.findViewById<EditText>(R.id.editText)
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        editText.requestFocus()

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val searchObserver = Observer<Boolean> { value ->
            value.let { searching ->
                editText.isEnabled = !searching
                progressBar.visibility = if (searching) View.VISIBLE else View.GONE
                menu?.findItem(R.id.clear)?.isEnabled = !searching
            }
        }

        viewModel.searching.observe(viewLifecycleOwner, searchObserver)

        val snackbarObserver = Observer<String> { value ->
            getView()?.let {
                Snackbar.make(it, value, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.snackbar.observe(viewLifecycleOwner, snackbarObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.clear)
        item.isEnabled = false
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.clear -> {
            // User chose the "Settings" item, show the app settings UI...
            Log.d(TAG, "clear")
            editText?.text.clear()
            true
        } else -> {
            true
        }
    }

    fun searchTerm(view: View) {
        val term = editText?.text.toString()

        imm.hideSoftInputFromWindow(editText?.windowToken, 0)

        viewModel.getDefinition(term) {
            findNavController()?.navigate(R.id.fragmentResults)
        }
    }

    override fun onPause() {
        super.onPause()

        imm.hideSoftInputFromWindow(editText?.windowToken, 0)

    }
}
