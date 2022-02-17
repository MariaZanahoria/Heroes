package com.wizeline.heroes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wizeline.heroes.R
import com.wizeline.heroes.adapters.CharacterAdapter
import com.wizeline.heroes.databinding.FragmentSearchBinding
import com.wizeline.heroes.viewModels.CharactersViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var catalogAdapter: CharacterAdapter
    private val catalogViewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catalogAdapter = CharacterAdapter { characterInfo ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                characterInfo.id ?: 0,
            )
            findNavController().navigate(action)
        }
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        catalogViewModel.getCharacters()

        // Get input text
        binding.svSearch.editText?.text.toString()

        // Respond to input text change
        binding.svSearch.editText?.doOnTextChanged { input, start, before, count ->

            Log.d("INPUT", "$input, $start, $before, $count")
        }

        binding.svSearch.editText?.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }
        catalogViewModel.characters.observe(viewLifecycleOwner) {
            catalogAdapter.submitList(it.data?.characterInfo)
        }
        catalogViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.rvHeroes.adapter = catalogAdapter
        return binding.root
    }

    private fun performSearch() {
        if (binding.svSearch.editText?.text.isNullOrEmpty()) {
            binding.svSearch.editText?.error = getString(R.string.error)
        } else {
            catalogViewModel.getSearchedCharacters(
                binding.svSearch.editText?.text?.toString().orEmpty()
            )
            catalogViewModel.searchedCharacters.observe(viewLifecycleOwner) {
                catalogAdapter.submitList(it.data?.characterInfo)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

