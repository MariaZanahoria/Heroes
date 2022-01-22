package com.wizeline.heroes.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wizeline.heroes.databinding.HeroItemBinding
import com.wizeline.heroes.model.Result
import com.bumptech.glide.request.RequestOptions
import com.wizeline.heroes.API_KEY
import com.wizeline.heroes.HASH
import com.wizeline.heroes.R
import com.wizeline.heroes.TS

class CharacterAdapter(val context: Context?) : ListAdapter<Result, CharacterAdapter.CatalogViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = HeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CatalogViewHolder(private val binding: HeroItemBinding, val context: Context?) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            val options = RequestOptions().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            Log.d("API", item.thumbnail.path + "/portrait_small.jpg?ts=$TS&apikey=$API_KEY&hash=$HASH")
            if (context != null) {
                Glide.with(context)
                    .load(item.thumbnail.path.replace("http", "https") + "/portrait_small.jpg?ts=$TS&apikey=$API_KEY&hash=$HASH")
                    .apply(options)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_search_purple)
                    .skipMemoryCache(true)//for caching the image url in case phone is offline
                    .into(binding.heroPhoto)
            }
        }
    }

    companion object {
        object ITEM_CALLBACK : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}
