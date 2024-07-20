package com.eliezer.marvel_characters.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class GenericAdapter<T,S: ItemGenericViewHolder<T>>(private val items: ArrayList<T>, protected val binding: (layoutInflater: LayoutInflater) -> ViewDataBinding) : RecyclerView.Adapter<S>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): S {
        val holder = binding.invoke(LayoutInflater.from(parent.context)).let {
            ItemGenericViewHolder<T>(it) as S
        }
        return holder
    }
    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: S, position: Int) {
        items[position].also {
            holder.bind(it)
        }
    }
}