package com.example.android.urbandictionary.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.urbandictionary.data.DefinitionItem
import com.example.android.urbandictionary.databinding.DefinitionItemBinding


class AdapterDefinitionResults(val onClickListener: OnClickListener) :
    ListAdapter<DefinitionItem, AdapterDefinitionResults.DefinitionViewHolder>(CallBack) {

    class DefinitionViewHolder(private var binding: DefinitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(definition: DefinitionItem) {
            binding.itemModel = definition
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object CallBack : DiffUtil.ItemCallback<DefinitionItem>() {
        override fun areItemsTheSame(oldItem: DefinitionItem, newItem: DefinitionItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DefinitionItem, newItem: DefinitionItem): Boolean {
            return oldItem.defid == newItem.defid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): DefinitionViewHolder {
        return DefinitionViewHolder(DefinitionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val definitionItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(definitionItem)
        }
        holder.bind(definitionItem)
    }

    class OnClickListener(val clickListener: (definitionItem:DefinitionItem) -> Unit) {
        fun onClick(definitionItem: DefinitionItem) = clickListener(definitionItem)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DefinitionItem>?) {
    val adapter = recyclerView.adapter as AdapterDefinitionResults
    adapter.submitList(data)
}