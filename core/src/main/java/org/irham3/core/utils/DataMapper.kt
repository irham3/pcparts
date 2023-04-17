package org.irham3.core.utils

import org.irham3.core.data.source.local.entity.ComponentEntity
import org.irham3.core.data.source.remote.response.ComponentResponse
import org.irham3.core.domain.model.Component

object DataMapper {
    fun mapResponsesToEntities(input: List<ComponentResponse>): List<ComponentEntity> {
        val componentList = ArrayList<ComponentEntity>()
        input.map {
            val tourism = ComponentEntity(
                componentId = it.id,
                description = it.description,
                name = it.name,
                url = it.url,
                price = it.price,
                image = it.imgFilename,
                isFavorite = false
            )
            componentList.add(tourism)
        }
        return componentList
    }

    fun mapEntitiesToDomain(input: List<ComponentEntity>): List<Component> =
        input.map {
            Component(
                componentId = it.componentId,
                description = it.description,
                name = it.name,
                url = it.url,
                price = it.price,
                image = it.image,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Component) = ComponentEntity(
        componentId = input.componentId,
        description = input.description,
        name = input.name,
        url = input.url,
        price = input.price,
        image = input.image,
        isFavorite = input.isFavorite
    )
}