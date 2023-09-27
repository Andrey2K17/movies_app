package com.pg13.moviesapp.ui.top_flims

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pg13.domain.entities.Films
import com.pg13.moviesapp.R
import com.pg13.moviesapp.databinding.FilmItemBinding

class TopFilmsAdapterPaging(private val onClickFavorite: (Films.Film) -> Unit) :
    PagingDataAdapter<Films.Film, TopFilmsAdapterPaging.TopFilmsViewHolder>(TopFilmsDiffCallbackPaging) {

    class TopFilmsViewHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val film = getItem(position)
        holder.binding.film = film
        holder.binding.favoriteCtv.setOnClickListener {
            val favorite = film!!.favorite
            holder.binding.favoriteCtv.setCheckMarkDrawable(if (favorite) R.drawable.add_favorite else R.drawable.favorite)
            onClickFavorite(film.also { it.favorite = !it.favorite })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        return TopFilmsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.film_item, parent, false
            )
        )
    }
}

object TopFilmsDiffCallbackPaging : DiffUtil.ItemCallback<Films.Film>() {
    override fun areItemsTheSame(oldItem: Films.Film, newItem: Films.Film): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Films.Film, newItem: Films.Film): Boolean {
        return oldItem == newItem
    }
}