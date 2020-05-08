package com.example.android.urbandictionary.single

import android.app.Application
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.android.urbandictionary.R

import com.example.android.urbandictionary.databinding.FragmentDefinitionItemBinding
import com.example.android.urbandictionary.search.ViewModelSearch
import kotlin.math.roundToInt

class FragmentDefinitionItem : Fragment() {

    private val TAG = FragmentDefinitionItem::class.java.canonicalName

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

        val binding = FragmentDefinitionItemBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.link).setOnClickListener { openPermanentLink(it) }

        val soundsContainer = view.findViewById<LinearLayout>(R.id.sounds)

        Log.d(TAG, "Size: ${viewModel.definition.value?.sound_urls?.size}")

        soundsContainer.visibility = View.VISIBLE

        var incremental = 0
        viewModel.definition.value?.sound_urls?.forEach {

            Log.d(TAG, "Iterating: ${it}")

            val button = Button(requireContext())
            button.id = incremental
            incremental++

            button.text = it
            button.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START

            val playIcon: Drawable = resources.getDrawable(R.drawable.ic_play_circle_filled_black_36dp, null)

            button.setCompoundDrawablesWithIntrinsicBounds(null, null, playIcon, null)
            
            button.setOnClickListener { playSound(it) }
            
            soundsContainer.addView(button)
        }

    }

    fun openPermanentLink(view: View) {
        val textView = view as TextView
        val url = textView.text
        val link = Intent(Intent.ACTION_VIEW)
        link.data = Uri.parse(url.toString())
        startActivity(link)
    }

    fun playSound(view: View) {

        val textView = view as TextView
        val mediaUrl = textView?.text.toString()
        val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
            Log.d(TAG, "Play some sound: $mediaUrl")
            setAudioAttributes(AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
            setDataSource(mediaUrl)
            prepareAsync() // might take long! (for buffering, etc)
            setOnPreparedListener { mp -> start() }
        }

    }

}
