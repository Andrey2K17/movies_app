package com.pg13.moviesapp.ui.top_flims

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pg13.domain.usecases.GetTopFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    getTopFilmsUseCase: GetTopFilmsUseCase,
) : ViewModel(){

    val films = getTopFilmsUseCase.invoke()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

}