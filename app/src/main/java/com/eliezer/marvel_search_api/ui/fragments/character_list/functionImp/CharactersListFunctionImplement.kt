package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.FragmentCharactersListBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.character_list.adapter.CharactersListAdapter
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function.CharacterListFunctionManagerRepository
import com.eliezer.marvel_search_api.ui.fragments.character_list.viewmodel.CharactersListViewModel

class CharactersListFunctionImplement(
    private val binding: FragmentCharactersListBinding,
    private val navigationMainActions: NavigationMainActions,
    private val  viewModel: CharactersListViewModel,
    private val characterListFunctionManagerRepository: CharacterListFunctionManagerRepository,
    private val owner : LifecycleOwner
) : CharactersListAdapter.CharacterHolderListener{

    private var adapter: CharactersListAdapter? = null
    private var searchCharacter : String? = null
    private val myOnScrolledListener = MyOnScrolledListener { getListCharacters()}
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)

    fun setAdapter() {
        adapter = CharactersListAdapter(arrayListOf(),this)
        binding.charactersListRecyclerView.setHasFixedSize(true)
        binding.charactersListRecyclerView.adapter = adapter
        binding.charactersListRecyclerView.addOnScrollListener(myOnScrolledListener)
    }
    fun getListSearchCharactersRepository()
    {
        searchCharacter?.also {
            val characters = characterListFunctionManagerRepository.getListRepository(it)
            setCharacterList(characters)
        }
    }
    fun getListFavoriteCharactersRepository(favoriteId : String)
    {
        val characters = characterListFunctionManagerRepository.getListRepository(favoriteId)
        setCharacterList(characters)
    }

    private fun setCharacterList(characters: Characters?) =
        adapter?.setCharacters(characters?.listCharacters ?: emptyList())

    override fun onCharacterItemClickListener(character: Character) {
        navigationMainActions.doActionCharacterListFragmentToCharacterProfileFragment(character =character)
    }
    fun getMode(arguments: Bundle) = CharactersListFragmentArgs.fromBundle(arguments).argMode

    fun getCharactersArg(arguments: Bundle) {
        searchCharacter = CharactersListFragmentArgs.fromBundle(arguments).argSearchCharacter
    }

    private fun setObservesVM() {
        viewModel.listCharacter.observe(owner,::setListCharacters)
    }

    private fun getListCharacters() {
        binding.charactersListRecyclerView.removeOnScrollListener(myOnScrolledListener)
        val characters = characterListFunctionManagerRepository.getListRepository(searchCharacter!!)
        if(characters==null || characters.total > characters.listCharacters.size)
        {
            searchListCharacters()
        }
        else if (adapter!!.isListEmpty())
        {
            setListCharacters(characters)
        }
    }

    private fun searchListCharacters() {
            setObservesVM()
            viewModel.searchCharactersList(searchCharacter!!)
    }

    private fun setListCharacters(characters: Characters?) {
        val position = myOnScrolledListener.position
        characters?.apply {
            if (listCharacters.isNotEmpty())
                adapter?.setCharacters(listCharacters)
        }
        binding.charactersListRecyclerView.scrollToPosition(position)
        resetRecyclerView()
        setNotObservesVM()
    }

    fun getIdCharacters(owner: LifecycleOwner) =   functionManagerViewModel.setIdCharactersObservesVM(owner,::getListCharactersByIds)

    private fun getListCharactersByIds(ids: ArrayList<Int>) {
        setObservesVM()
        viewModel.getFavoriteComicsList(ids)
    }

    private fun setNotObservesVM() {
        viewModel.listCharacter.removeObservers(owner)
        viewModel.resetCharacters()
    }
    private fun resetRecyclerView() {
        binding.charactersListRecyclerView.addOnScrollListener(myOnScrolledListener)
    }

    override fun onImageButtonFavoriteListener(character: Character) =
        if(character.favorite)
            characterListFunctionManagerRepository.insertFavoriteCharacter(character.id)
        else
            characterListFunctionManagerRepository.deleteFavoriteCharacter(character.id)
}
private class FunctionManagerViewModel(
    private val viewModel: CharactersListViewModel
)
{
    fun setIdCharactersObservesVM(owner: LifecycleOwner, observeCharacters: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdCharacters.observe(owner,observeCharacters)
    }
    fun setIdCharactersNotObservesVM(owner: LifecycleOwner) {
        viewModel.favoriteIdCharacters.removeObservers(owner)
        viewModel.resetFavoriteIdCharacters()
    }
}