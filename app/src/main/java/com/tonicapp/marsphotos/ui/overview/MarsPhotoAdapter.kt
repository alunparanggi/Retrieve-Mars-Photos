package com.tonicapp.marsphotos.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tonicapp.marsphotos.databinding.ItemMarsBinding
import com.tonicapp.marsphotos.network.MarsProperty

class MarsPhotoAdapter:
    ListAdapter<MarsProperty, MarsPhotoAdapter.MarsPhotoViewHolder>(DiffCallback) {

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }


    inner class MarsPhotoViewHolder(private var binding: ItemMarsBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(marsProperty: MarsProperty){
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        val binding = ItemMarsBinding.inflate(LayoutInflater.from(parent.context))
        return MarsPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
    }

}