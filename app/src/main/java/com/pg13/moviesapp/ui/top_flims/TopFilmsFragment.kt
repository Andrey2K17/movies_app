package com.pg13.moviesapp.ui.top_flims

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.OrderType
import com.pg13.moviesapp.R
import com.pg13.moviesapp.databinding.FragmentTopFilmsBinding
import com.pg13.moviesapp.ui.base.ViewBindingFragment
import com.pg13.moviesapp.utils.launchOnLifecycle
import com.pg13.moviesapp.utils.toStringOrEmpty
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
        TopFilmsAdapterPaging(this::adapterOnClickFavorite).apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
                viewModel.setQuery(text.toStringOrEmpty())
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

            launchOnLifecycle {
                viewModel.favoriteFilmsIsEmpty.collectLatest {
                    favoritesButton.visibility = if (it) View.GONE else View.VISIBLE
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

            sortButton.setOnClickListener {
                sortingButtons.visibility = if (sortingButtons.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            byPopularityTextView.setOnClickListener { updateFilms(OrderType.NUM_VOTE) }
            byDefaultTextView.setOnClickListener { updateFilms(OrderType.DEFAULT) }
            byRatingTextView.setOnClickListener { updateFilms(OrderType.RATING) }
        }
    }

    private fun updateFilms(orderType: OrderType) {
        viewModel.updateFilms(orderType)
        binding.sortingButtons.visibility = View.GONE
        binding.query.text.clear()
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.query) {
            if (text.isEmpty()) {
                viewModel.updateFilms()
            }
            if ((text.toStringOrEmpty()) != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    private fun adapterOnClickFavorite(film: Films.Film) {
        viewModel.addToFavorite(film)
    }
}