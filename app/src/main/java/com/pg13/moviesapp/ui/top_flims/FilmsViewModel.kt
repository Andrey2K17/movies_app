package com.pg13.moviesapp.ui.top_flims

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.OrderType
import com.pg13.domain.usecases.AddToFavoriteUseCase
import com.pg13.domain.usecases.FavoriteFilmsSizeUseCase
import com.pg13.domain.usecases.GetTopFilmsUseCase
import com.pg13.domain.usecases.SearchFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    getTopFilmsUseCase: GetTopFilmsUseCase,
    private val searchFilmsUseCase: SearchFilmsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val favoriteFilmsSizeUseCase: FavoriteFilmsSizeUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val refreshEvent = MutableSharedFlow<Unit>(1)

    private var order: OrderType = OrderType.EMPTY

    private val _favoriteFilmsIsEmpty = MutableStateFlow(true)
    val favoriteFilmsIsEmpty = _favoriteFilmsIsEmpty.asStateFlow()

    init {
        viewModelScope.launch {
            updateFilms()
            _isLoading.value = false
            getFavoriteFilmsSize()
        }
    }

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    //private var newPagingSource: PagingSource<*, *>? = null

    val films: StateFlow<PagingData<Films.Film>> =
        refreshEvent.flatMapLatest { getTopFilmsUseCase.invoke(order) }
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = PagingData.empty()
            )



    val searchFilms: StateFlow<PagingData<Films.Film>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: String): Pager<Int, Films.Film> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            //newPagingSource?.invalidate()
            val queryNewsUseCase = searchFilmsUseCase
            queryNewsUseCase(query)
                //.also { newPagingSource = it }
        }
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }

    fun updateFilms(order: OrderType = OrderType.EMPTY) {
        this.order = order
        viewModelScope.launch {
            refreshEvent.emit(Unit)
            cancel()
        }
    }

    fun addToFavorite(film: Films.Film) {
        viewModelScope.launch(Dispatchers.IO) {
            val row: Int = addToFavoriteUseCase.invoke(film)
            if (row != -1) {
                updateFilms(order)
                getFavoriteFilmsSize()
            }
        }
    }

    private fun getFavoriteFilmsSize() {
        viewModelScope.launch {
            val isEmpty = favoriteFilmsSizeUseCase.invoke() == 0
            if (isEmpty != _favoriteFilmsIsEmpty.value) {
                _favoriteFilmsIsEmpty.value = !_favoriteFilmsIsEmpty.value
            }
        }
    }
}