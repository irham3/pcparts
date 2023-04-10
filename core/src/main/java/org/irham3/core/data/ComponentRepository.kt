package org.irham3.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.irham3.core.data.source.local.LocalDataSource
import org.irham3.core.data.source.remote.RemoteDataSource
import org.irham3.core.data.source.remote.network.ApiResponse
import org.irham3.core.data.source.remote.response.ComponentResponse
import org.irham3.core.domain.model.Component
import org.irham3.core.domain.repository.IComponentRepository
import org.irham3.core.utils.AppExecutors
import org.irham3.core.utils.DataMapper

class ComponentRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IComponentRepository {

    override fun getAllComponent(): Flow<Resource<List<Component>>> =
        object : NetworkBoundResource<List<Component>, List<ComponentResponse>>() {
            override fun loadFromDB(): Flow<List<Component>> {
                return localDataSource.getAllComponent().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Component>?): Boolean =
//                data == null || data.isEmpty()
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ComponentResponse>>> =
                remoteDataSource.getAllPart()

            override suspend fun saveCallResult(data: List<ComponentResponse>) {
                val componentList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertComponent(componentList)
            }
        }.asFlow()

    override fun getFavoriteComponent(): Flow<List<Component>> {
        return localDataSource.getFavoriteComponent().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteComponent(component: Component, state: Boolean) {
        val componentEntity = DataMapper.mapDomainToEntity(component)
        appExecutors.diskIO().execute { localDataSource.setFavoriteComponent(componentEntity, state) }
    }
}