package org.irham3.pcparts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.irham3.core.domain.model.Component
import org.irham3.core.domain.usecase.ComponentUseCase

class DetailComponentViewModel (private val componentUseCase: ComponentUseCase): ViewModel() {
    fun setFavouriteComponent(component: Component, status: Boolean) =
        componentUseCase.setFavoriteComponent(component, status)

//    val favoriteComponent = componentUseCase.getFavoriteComponent().asLiveData()
}