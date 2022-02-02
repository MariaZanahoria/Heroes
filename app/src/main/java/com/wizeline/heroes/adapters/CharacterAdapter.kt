package com.wizeline.heroes.adapters

import android.content.Context
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

class CharacterAdapter(private val context: Context?, private val onClickListener:(Result)->Unit) :
    ListAdapter<Result, CharacterAdapter.CatalogViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = HeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val characterItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.invoke(characterItem)
        }
        holder.bind(characterItem)
    }

    class OnClickListener(val clickListener: (characterItem: Result) -> Unit) {
        fun onClick(characterItem: Result) = clickListener(characterItem)
    }

    class CatalogViewHolder(private val binding: HeroItemBinding, private val context: Context?) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            val options = RequestOptions().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            if (context != null) {
                Glide.with(context)
                    .asBitmap()
                    .load(item.thumbnail?.path?.replace("http", "https") + "/portrait_small.jpg?ts=$TS&apikey=$API_KEY&hash=$HASH")
                    .apply(options)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
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
