package com.sapan.tmdbapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapan.tmdbapp.databinding.FragmentSearchBinding
import com.sapan.tmdbapp.viewmodel.SearchViewModel
import com.sapan.tmdbapp.views.adapter.BookmarkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchScreen : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: BookmarkAdapter

    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = arguments?.getString(ARG_SEARCH_QUERY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeSearchResults()
        searchQuery?.let { viewModel.setSearchQuery(it) }
    }

    private fun setupRecyclerView() {
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter = BookmarkAdapter()
        binding.searchRecyclerView.adapter = searchAdapter
    }

    private fun observeSearchResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResults.collectLatest { bookmarks ->
                    if (_binding != null) {
                        searchAdapter.submitList(bookmarks)
                        if (bookmarks.isEmpty()) {
                            binding.errorMessage.visibility = View.VISIBLE
                            binding.errorMessage.text = "No results found"
                        } else {
                            binding.errorMessage.visibility = View.GONE
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collectLatest { isLoading ->
                    if (_binding != null) {
                        if (isLoading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SEARCH_QUERY = "search_query"

        fun newInstance(query: String): SearchScreen {
            val fragment = SearchScreen()
            val args = Bundle().apply {
                putString(ARG_SEARCH_QUERY, query)
            }
            fragment.arguments = args
            return fragment
        }
    }
}