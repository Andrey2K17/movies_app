package com.pg13.moviesapp.ui.top_flims

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pg13.domain.entities.Films
import com.pg13.moviesapp.R
import com.pg13.moviesapp.databinding.FilmItemBinding

class TopFilmsAdapter :
    ListAdapter<Films.Film, TopFilmsAdapter.TopFilmsViewHolder>(TopFilmsDiffCallback) {

    class TopFilmsViewHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        return TopFilmsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.film_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val film = getItem(position)
        holder.binding.film = film
    }
}

object TopFilmsDiffCallback : DiffUtil.ItemCallback<Films.Film>() {
    override fun areItemsTheSame(oldItem: Films.Film, newItem: Films.Film): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Films.Film, newItem: Films.Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }
}