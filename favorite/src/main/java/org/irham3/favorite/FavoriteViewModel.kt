package org.irham3.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.irham3.core.domain.usecase.ComponentUseCase

class FavoriteViewModel (componentUseCase: ComponentUseCase) : ViewModel() {
    val favoriteComponent = componentUseCase.getFavoriteComponent().asLiveData()
}