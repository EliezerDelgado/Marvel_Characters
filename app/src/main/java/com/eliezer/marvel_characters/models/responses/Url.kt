package com.eliezer.marvel_characters.models.responses

import com.google.gson.annotations.SerializedName


data class Url (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)