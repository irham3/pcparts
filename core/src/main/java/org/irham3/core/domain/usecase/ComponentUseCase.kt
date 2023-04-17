package org.irham3.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.irham3.core.data.Resource
import org.irham3.core.domain.model.Component

interface ComponentUseCase {
    fun getAllComponent(): Flow<Resource<List<Component>>>
    fun getFavoriteComponent(): Flow<List<Component>>
    fun setFavoriteComponent(component: Component, state: Boolean)
}