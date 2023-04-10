package org.irham3.core.domain.usecase

import org.irham3.core.domain.model.Component
import org.irham3.core.domain.repository.IComponentRepository

class ComponentInteractor(private val componentRepository: IComponentRepository): TourismUseCase {

    override fun getAllComponent() = componentRepository.getAllComponent()
    override fun getFavoriteComponent() = componentRepository.getFavoriteComponent()
    override fun setFavoriteComponent(component: Component, state: Boolean) = componentRepository.setFavoriteComponent(component, state)
}