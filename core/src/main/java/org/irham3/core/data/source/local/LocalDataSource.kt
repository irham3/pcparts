package org.irham3.core.data.source.local

import kotlinx.coroutines.flow.Flow
import org.irham3.core.data.source.local.entity.ComponentEntity
import org.irham3.core.data.source.local.room.ComponentDao

class LocalDataSource(private val componentDao: ComponentDao) {

    fun getAllComponent(): Flow<List<ComponentEntity>> = componentDao.getAllComponent()

    fun getFavoriteComponent(): Flow<List<ComponentEntity>> = componentDao.getFavoriteComponent()

    suspend fun insertComponent(componentList: List<ComponentEntity>) = componentDao.insertComponent(componentList)

    fun setFavoriteComponent(component: ComponentEntity, newState: Boolean) {
        component.isFavorite = newState
        componentDao.updateFavoriteComponent(component)
    }
}