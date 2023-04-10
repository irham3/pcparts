package org.irham3.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.irham3.core.data.source.local.entity.ComponentEntity

@Database(entities = [ComponentEntity::class], version = 1, exportSchema = false)
abstract class ComponentDatabase : RoomDatabase() {

    abstract fun componentDao(): ComponentDao

}