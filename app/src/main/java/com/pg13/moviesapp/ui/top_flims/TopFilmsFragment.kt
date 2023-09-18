package com.pg13.moviesapp.ui.top_flims

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.pg13.moviesapp.R
import com.pg13.moviesapp.databinding.FragmentTopFilmsBinding
import com.pg13.moviesapp.ui.base.ViewBindingFragment
import com.pg13.moviesapp.utils.launchOnLifecycle
import com.pg13.mycitchen.ui.rv.HorizontalSpaceItemDecoration
import com.pg13.mycitchen.ui.rv.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TopFilmsFragment : ViewBindingFragment<FragmentTopFilmsBinding>() {
    override fun getLayoutId() = R.layout.fragment_top_films

    private val viewModel: FilmsViewModel by viewModels()

    private val adapter: TopFilmsAdapterPaging by lazy {
        TopFilmsAdapterPaging().apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
//            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
//                FilmsLoaderStateAdapter(),
//                FilmsLoaderStateAdapter()
//            )
//
//            adapter.addLoadStateListener { state: CombinedLoadStates ->
//                recyclerView.isVisible = state.refresh != LoadState.Loading
//                progress.isVisible = state.refresh == LoadState.Loading
//            }

            query.doAfterTextChanged { text ->
                viewModel.setQuery(text?.toString() ?: "")
            }

            launchOnLifecycle {
                viewModel.searchFilms.collectLatest {
                    adapter.submitData(it)
                }
            }

            launchOnLifecycle {
                viewModel.films.collect { data ->
                    adapter.submitData(data)
                }
            }

            viewModel.query
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .onEach(::updateSearchQuery)
                .launchIn(lifecycleScope)

            recyclerView.addItemDecoration(
                VerticalSpaceItemDecoration(
                    requireContext().resources.getDimensionPixelSize(
                        R.dimen.padding_8dp
                    )
                )
            )
            recyclerView.addItemDecoration(
                HorizontalSpaceItemDecoration(
                    requireContext().resources.getDimensionPixelSize(
                        R.dimen.padding_8dp
                    )
                )
            )
        }
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.query) {
            if (text.isEmpty()) {
                viewModel.updateFilms()
            }
            if ((text?.toString() ?: "") != searchQuery) {
                setText(searchQuery)
            }
        }
    }
}