package com.wizeline.heroes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wizeline.heroes.adapters.CharacterAdapter
import com.wizeline.heroes.databinding.FragmentCatalogBinding
import com.wizeline.heroes.viewModels.CharactersViewModel

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var catalogAdapter: CharacterAdapter
    private val catalogViewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catalogAdapter = CharacterAdapter { characterInfo ->
            val action = CatalogFragmentDirections.actionCatalogFragmentToDetailsFragment(
                characterInfo.id ?: 0,
                characterInfo.name.orEmpty(),
                characterInfo.description.orEmpty(),
                characterInfo.thumbnail?.getUrl() ?: ""
            )
            findNavController().navigate(action)
        }
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        catalogViewModel.getCharacters()
        catalogViewModel.characters.observe(viewLifecycleOwner) {
            catalogAdapter.submitList(it.data?.characterInfo)
        }
        catalogViewModel.errorMessage.observe(viewLifecycleOwner) {
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