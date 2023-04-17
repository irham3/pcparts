package org.irham3.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ComponentResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("img_filename")
	val imgFilename: String,

	@field:SerializedName("description")
	val description: String
)
