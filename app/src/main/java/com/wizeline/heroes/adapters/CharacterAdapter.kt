package com.wizeline.heroes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wizeline.heroes.databinding.HeroItemBinding
import com.wizeline.heroes.model.CharacterInfo
import com.bumptech.glide.request.RequestOptions
import com.wizeline.heroes.R

class CharacterAdapter(private val onClickListener: (CharacterInfo) -> Unit) :
    ListAdapter<CharacterInfo, CharacterAdapter.CatalogViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = HeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val characterItem = getItem(position)
        holder.bind(characterItem)
    }

    inner class CatalogViewHolder(private val binding: HeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterInfo) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            binding.root.setOnClickListener { onClickListener(item) }
            val options = RequestOptions().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            Glide.with(binding.root.context)
                .asBitmap()
                .load(item.thumbnail?.getUrl())
                .apply(options)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .skipMemoryCache(true)//for caching the image url in case phone is offline
                .into(binding.heroPhoto)
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
