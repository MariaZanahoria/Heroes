package com.wizeline.heroes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wizeline.heroes.API_KEY
import com.wizeline.heroes.HASH
import com.wizeline.heroes.R
import com.wizeline.heroes.TS
import com.wizeline.heroes.databinding.SeriesItemBinding
import com.wizeline.heroes.model.CharacterInfo

class CharacterSeriesAdapter : ListAdapter<CharacterInfo, CharacterSeriesAdapter.SeriesViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = SeriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SeriesViewHolder(private val binding: SeriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterInfo) {
            Glide.with(binding.root.context)
                .load(
                    item.thumbnail?.path?.replace(
                        "http",
                        "https"
                    ) + "/portrait_small.jpg?ts=$TS&apikey=$API_KEY&hash=$HASH"
                )
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .skipMemoryCache(true)//for caching the image url in case phone is offline
                .into(binding.ivSeries)
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