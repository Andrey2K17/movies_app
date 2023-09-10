package com.pg13.moviesapp.ui.top_flims

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pg13.domain.entities.Films
import com.pg13.domain.usecases.GetTopFilmsUseCase
import com.pg13.moviesapp.utils.FilmsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
) : ViewModel(){

    private val factory = FilmsPagingSource(getTopFilmsUseCase)

    val films: StateFlow<PagingData<Films.Film>> = Pager(PagingConfig(pageSize = 5, enablePlaceholders = false)) {
        factory
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}