package com.eliezer.marvel_characters.ui.fragments.favorites
import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_characters.ui.fragments.favorites.adapter.FavoritesPagerAdapter
import com.eliezer.marvel_characters.ui.fragments.favorites.functionImp.FavoritesFunctionImplement

class FavoritesFragment :  BaseFragment<FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
)  {
    private var pagerAdapter : FavoritesPagerAdapter? = null
    private var funImpl : FavoritesFunctionImplement? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = FavoritesPagerAdapter(this)
        funImpl = FavoritesFunctionImplement(binding,pagerAdapter!!,requireContext())
        funImpl?.setFragments()
        funImpl?.setContentView()
        funImpl?.createTabLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
        pagerAdapter=null
    }
}