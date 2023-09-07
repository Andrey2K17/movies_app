package com.pg13.moviesapp.ui.top_flims

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import com.pg13.domain.usecases.GetTopFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getTopFilmsUseCase: GetTopFilmsUseCase
) : ViewModel(){

    private val refreshEvent = MutableSharedFlow<Unit>(1)

    val films: Flow<Resource<Films>> =
        refreshEvent
            .flatMapLatest { getTopFilmsUseCase.invoke() }

    fun getTopFilms() {
        viewModelScope.launch {
            refreshEvent.emit(Unit)
            cancel()
        }
    }

}