package com.eliezer.marvel_characters.models.responses.comic

import com.google.gson.annotations.SerializedName


data class StoryList (

  @SerializedName("available"     ) var available     : Int?             = null,
  @SerializedName("returned"      ) var returned      : Int?             = null,
  @SerializedName("collectionURI" ) var collectionURI : String?          = null,
  @SerializedName("items"         ) var items         : ArrayList<StorySummary> = arrayListOf()
)