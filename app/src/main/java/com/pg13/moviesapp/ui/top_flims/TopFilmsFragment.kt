package com.pg13.moviesapp.ui.top_flims

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import com.pg13.moviesapp.R
import com.pg13.moviesapp.databinding.FragmentTopFilmsBinding
import com.pg13.moviesapp.ui.base.ViewBindingFragment
import com.pg13.moviesapp.utils.launchOnLifecycle
import com.pg13.moviesapp.utils.showErrorDialog
import com.pg13.mycitchen.ui.rv.HorizontalSpaceItemDecoration
import com.pg13.mycitchen.ui.rv.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFilmsFragment : ViewBindingFragment<FragmentTopFilmsBinding>() {
    override fun getLayoutId() = R.layout.fragment_top_films

    private val viewModel: FilmsViewModel by viewModels()

    private val adapter: TopFilmsAdapter by lazy {
        TopFilmsAdapter()
    }

    private var films = listOf<Films.Film>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTopFilms()

        with(binding) {
            recyclerView.adapter = adapter
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

            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filterList(newText)
                    return true
                }
            })

            launchOnLifecycle {
                viewModel.films.collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            refreshLayout.isRefreshing = false
                            resource.message?.let { showErrorDialog(it) }
                        }

                        is Resource.Loading -> refreshLayout.isRefreshing = true
                        is Resource.Success -> {
                            refreshLayout.isRefreshing = false
                            adapter.submitList(resource.data.films)
                            films = resource.data.films
                        }
                    }
                }
            }
        }
    }

    private fun filterList(query: String) {
        if (query.isNotEmpty()) {
            val filteredList = ArrayList<Films.Film>()
            films.map { film ->
                if (film.nameRu.lowercase().contains(query)) {
                    filteredList.add(film)
                }
            }
            adapter.submitList(filteredList)
        } else {
            viewModel.getTopFilms()
        }
    }

}