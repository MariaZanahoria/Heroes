package com.wizeline.heroes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wizeline.heroes.adapters.CharacterAdapter
import com.wizeline.heroes.databinding.FragmentCatalogBinding
import com.wizeline.heroes.viewModels.CharactersViewModel

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var catalogAdapter : CharacterAdapter
    private lateinit var catalogViewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catalogAdapter = CharacterAdapter(context)
        catalogViewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        initRecyclerView()
        catalogViewModel.getCharacters()
        catalogViewModel.characters.observe(viewLifecycleOwner) {
//            Log.d("TS", "TS: $TS")
            Log.d("Isela", "onCreateView: $it")
            catalogAdapter.submitList(it.data.results)
        }
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvHeroes.adapter = catalogAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}