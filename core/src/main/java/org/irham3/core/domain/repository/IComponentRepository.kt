package org.irham3.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.irham3.core.data.Resource
import org.irham3.core.domain.model.Component

interface IComponentRepository {

    fun getAllComponent(): Flow<Resource<List<Component>>>
    fun getFavoriteComponent(): Flow<List<Component>>
    fun setFavoriteComponent(component: Component, state: Boolean)

}