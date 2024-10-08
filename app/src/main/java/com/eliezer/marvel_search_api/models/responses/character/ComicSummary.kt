package com.eliezer.marvel_search_api.models.responses.character

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ComicSummary(
    @SerializedName("resourceURI"     ) var resourceURI: String? = null,
    @SerializedName("name"     ) var name: String? = null
)