package com.wizeline.heroes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wizeline.heroes.R
import com.wizeline.heroes.databinding.ComicsItemBinding
import com.wizeline.heroes.model.CharacterInfo

class CharacterComicsAdapter :
    ListAdapter<CharacterInfo, CharacterComicsAdapter.ComicsViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val view = ComicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ComicsViewHolder(private val binding: ComicsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterInfo) {
            Glide.with(binding.root.context)
                .load(item.thumbnail?.getUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .skipMemoryCache(true)//for caching the image url in case phone is offline
                .into(binding.ivComics)
        }
    }

    companion object {
        object ITEM_CALLBACK : DiffUtil.ItemCallback<CharacterInfo>() {
            override fun areItemsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CharacterInfo,
                newItem: CharacterInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}