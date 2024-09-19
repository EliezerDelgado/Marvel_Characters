package com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel

class MarvelSearchFunctionImplement(
    private val binding: FragmentMarvelSearchBinding,
    private val viewModel: MarvelSearchViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val owner : LifecycleOwner
) {
    private var nameButtonPulse : String? = null

    fun buttonListener(){
        binding.apply {
            marvelSearchButtonGoComicsList.apply {
                setOnClickListener {
                    val textSearch  = marvelSearchTextInputSearch.editText?.text.toString()
                    setObservesVM()
                    nameButtonPulse = id.toString()
                    searchListComics(textSearch)
                }
            }
            marvelSearchButtonGoCharacterList.apply {
                setOnClickListener {
                    val textSearch  = marvelSearchTextInputSearch.editText?.text.toString()
                    setObservesVM()
                    nameButtonPulse = id.toString()
                    searchListCharacters(textSearch)
                }
            }
            marvelSearchImageButtonGoFavorite.apply {
                setOnClickListener {
                    nameButtonPulse = marvelSearchImageButtonGoFavorite.id.toString()
                    moveFragment()
                }
            }
        }
    }


    private fun goCharacterListFragment()=
        navigationMainActions.doActionMarvelSearchFragmentToCharacterListFragment(   binding.marvelSearchTextInputSearch.editText?.text.toString())
    private fun goComicsListFragment() = navigationMainActions.doActionMarvelSearchFragmentToComicListFragment(   binding.marvelSearchTextInputSearch.editText?.text.toString())
    private fun setObservesVM() {
        viewModel.sizeResult.observe(owner,::getSizeResultList)
    }
    private fun searchListCharacters(name:String)
    {
        disableButtons()
        viewModel.searchCharactersList(name)
    }
    private fun searchListComics(title: String) {
        disableButtons()
        viewModel.searchComicsList(title)
    }
    private fun disableButtons() {
        binding.marvelSearchButtonGoComicsList.isEnabled = false
        binding.marvelSearchButtonGoCharacterList.isEnabled = false
    }
    private fun enableButtons() {
        binding.marvelSearchButtonGoComicsList.isEnabled = true
        binding.marvelSearchButtonGoCharacterList.isEnabled = true
    }

    private fun getSizeResultList(size: Int){
        if(size>0) {
            moveFragment()
        }
        else if(size==0)
            showError(R.string.marvel_search_button_go_character_list__text)
        enableButtons()
        setNotObservesVM()
    }

    private fun moveFragment() {
        when(nameButtonPulse)
        {
            binding.marvelSearchButtonGoComicsList.id.toString() -> goComicsListFragment()
            binding.marvelSearchButtonGoCharacterList.id.toString() -> goCharacterListFragment()
            binding.marvelSearchImageButtonGoFavorite!!.id.toString() -> goFavoriteFragment()
        }
        nameButtonPulse=null
    }

    private fun goFavoriteFragment() {
        navigationMainActions.doActionMarvelSearchFragmentToFavoritesFragment()
    }

    private fun showError(@StringRes idError: Int) {
        //todo e un utils
    }

    private fun setNotObservesVM() {
        viewModel.sizeResult.removeObservers(owner)
        viewModel.resetSizeResult()
    }

    fun resetLists() {
        viewModel.resetLists()
    }
}
