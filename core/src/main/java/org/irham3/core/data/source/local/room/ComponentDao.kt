package org.irham3.core.data.source.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.irham3.core.data.source.local.entity.ComponentEntity

@Dao
interface ComponentDao {

    @Query("SELECT * FROM component")
    fun getAllComponent(): Flow<List<ComponentEntity>>

    @Query("SELECT * FROM component WHERE isFavorite = 1")
    fun getFavoriteComponent(): Flow<List<ComponentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponent(component: List<ComponentEntity>)

    @Update
    fun updateFavoriteComponent(component: ComponentEntity)
}