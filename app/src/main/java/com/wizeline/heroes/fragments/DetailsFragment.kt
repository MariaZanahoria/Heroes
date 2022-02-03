package com.wizeline.heroes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wizeline.heroes.R
import com.wizeline.heroes.adapters.CharacterComicsAdapter
import com.wizeline.heroes.adapters.CharacterSeriesAdapter
import com.wizeline.heroes.databinding.FragmentDetailsBinding
import com.wizeline.heroes.viewModels.DetailCharacterViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var comicsAdapter: CharacterComicsAdapter
    private lateinit var seriesAdapter: CharacterSeriesAdapter
    private val detailViewModel: DetailCharacterViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val amount = args.characterId
        initViews()
        initAdapters()
        initObservers(amount)
        return binding.root
    }

    private fun initObservers(amount: Int) {
        detailViewModel.getCharacterComics(amount)
        detailViewModel.getCharacterSeries(amount)
        detailViewModel.comics.observe(viewLifecycleOwner) {
            comicsAdapter.submitList(it.data?.characterInfo)
        }
        detailViewModel.series.observe(viewLifecycleOwner) {
            seriesAdapter.submitList(it.data?.characterInfo)
        }
        detailViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapters() {
        comicsAdapter = CharacterComicsAdapter()
        seriesAdapter = CharacterSeriesAdapter()
        binding.rvComics.adapter = comicsAdapter
        binding.rvSeries.adapter = seriesAdapter
    }

    private fun initViews() {
        binding.tvNameHero.text = args.characterName
        binding.tvDescription.text = args.characterDescription
        val options = RequestOptions().centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(binding.root.context)
            .asBitmap()
            .load(args.characterPhoto)
            .apply(options)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .override(450, 450)
            .skipMemoryCache(true)//for caching the image url in case phone is offline
            .into(binding.ivHero)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}