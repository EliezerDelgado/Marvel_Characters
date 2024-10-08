package com.eliezer.marvel_search_api.data.repository.comics.mock

import com.eliezer.marvel_search_api.domain.repository.ComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject

class SetComicsRepository @Inject constructor(
    private val comicsRepository: ComicsRepository
){
    fun setListRepository(id : String,params: Comics){
        comicsRepository.setListComics(id,params)
    }

    fun resetListRepository() {
        comicsRepository.resetList()
    }
}