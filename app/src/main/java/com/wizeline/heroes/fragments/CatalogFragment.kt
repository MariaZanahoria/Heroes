package com.wizeline.heroes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.wizeline.heroes.adapters.CharacterAdapter
import com.wizeline.heroes.databinding.FragmentCatalogBinding
import com.wizeline.heroes.viewModels.CharactersViewModel

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var catalogAdapter : CharacterAdapter
    private val catalogViewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catalogAdapter = CharacterAdapter(context, {result ->  })
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        catalogViewModel.getCharacters()
        catalogViewModel.characters.observe(viewLifecycleOwner) {
            catalogAdapter.submitList(it.data?.results)
        }
        catalogViewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        binding.rvHeroes.adapter = catalogAdapter
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}