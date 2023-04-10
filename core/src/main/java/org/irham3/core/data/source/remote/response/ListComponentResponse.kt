package org.irham3.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListComponentResponse(

	@field:SerializedName("Response")
	val response: List<ComponentResponse>
)
