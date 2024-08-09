package com.eliezer.marvel_characters.models.responses.character

import com.google.gson.annotations.SerializedName

data class ComicSummary(
    @SerializedName("resourceURI"     ) var resourceURI: String? = null,
    @SerializedName("name"     ) var name: String? = null
)