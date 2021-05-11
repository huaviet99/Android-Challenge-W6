package com.thesis.android_challenge_w6.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.model.Restaurant
import com.thesis.android_challenge_w6.movie.TopRatedMovies

class TopRatedListAdapter :
    ListAdapter<TopRatedMovies, TopRatedListAdapter.ViewHolder>(RestaurantDiffUtilCallback()) {
    companion object {
        const val LINEAR_ITEM = 0
        const val GRID_ITEM = 1
    }

    var isLinearSwitched = true
    var listener: TopRatedAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View?
        view = if (viewType == LINEAR_ITEM) {
            inflater.inflate(R.layout.item_linear_movie, parent, false)
        } else {
            inflater.inflate(R.layout.item_grid_movie, parent, false)
        }
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLinearSwitched) {
            LINEAR_ITEM
        } else {
            GRID_ITEM
        }
    }

    fun toggleItemViewType(): Boolean {
        isLinearSwitched = !isLinearSwitched
        return isLinearSwitched
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMovieName: TextView? = itemView.findViewById(R.id.tv_movie_name)
        private val tvMovieGenre: TextView? = itemView.findViewById(R.id.tv_movie_genre)
        private val imgMovie: ImageView? = itemView.findViewById(R.id.img_movie)
        private val tvMoviesYear: TextView? = itemView.findViewById(R.id.tv_movie_year)
        fun bind(movies: TopRatedMovies, listener: TopRatedAdapterListener) {
            itemView.setOnClickListener {
                listener.onItemClicked(movies)
            }

            if (isLinearSwitched) {
                tvMovieName!!.text = movies.title
                tvMovieGenre!!.text = "k CO "
                tvMoviesYear!!.text = movies.releaseDate
                Glide.with(itemView.context)
                    .load("https://api.themoviedb.org/3" + movies.backdropPath)
                    .into(imgMovie!!)
            } else {
                tvMovieName!!.text = movies.title
                tvMoviesYear!!.text = movies.releaseDate
                Glide.with(itemView.context)
                    .load("https://api.themoviedb.org/3" + movies.backdropPath)
                    .into(imgMovie!!)
            }
        }

    }

    class RestaurantDiffUtilCallback : DiffUtil.ItemCallback<TopRatedMovies>() {
        override fun areItemsTheSame(oldItem: TopRatedMovies, newItem: TopRatedMovies): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TopRatedMovies, newItem: TopRatedMovies): Boolean {
            return oldItem == newItem
        }
    }

    interface TopRatedAdapterListener {
        fun onItemClicked(movies: TopRatedMovies)
    }
}