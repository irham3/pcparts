package org.irham3.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Component(
    val componentId: Int,
    val name: String,
    val description: String,
    val price: Int,
    val url: String,
    val image: String,
    val isFavorite: Boolean
) : Parcelable