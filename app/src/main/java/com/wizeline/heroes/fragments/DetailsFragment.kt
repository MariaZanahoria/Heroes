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
import com.wizeline.heroes.API_KEY
import com.wizeline.heroes.HASH
import com.wizeline.heroes.R
import com.wizeline.heroes.TS
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
        binding.tvNameHero.text = args.characterName
        binding.tvDescription.text = args.characterDescription
//        Glide.with(binding.root.context)
//            .asBitmap()
//            .load(args.characterPhoto.replace("http", "https") + "/portrait_small.jpg?ts=$TS&apikey=$API_KEY&hash=$HASH")
//            .centerCrop()
//            .placeholder(R.drawable.ic_launcher_foreground)
//            .error(R.drawable.ic_launcher_foreground)
//            .skipMemoryCache(true)//for caching the image url in case phone is offline
//            .into(binding.ivHero)

        comicsAdapter = CharacterComicsAdapter()
        seriesAdapter = CharacterSeriesAdapter()
        binding.rvComics.adapter = comicsAdapter
        binding.rvSeries.adapter = seriesAdapter
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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}