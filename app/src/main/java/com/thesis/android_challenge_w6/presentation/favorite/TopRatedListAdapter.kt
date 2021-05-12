package com.thesis.android_challenge_w6.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.api.movie.Movie

class TopRatedListAdapter :
    ListAdapter<Movie, TopRatedListAdapter.ViewHolder>(RestaurantDiffUtilCallback()) {
    companion object {
        const val LINEAR_ITEM = 0
        const val GRID_ITEM = 1
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w500"
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
        private val imgMovie: ImageView? = itemView.findViewById(R.id.img_movie)
        private val tvMoviesYear: TextView? = itemView.findViewById(R.id.tv_movie_year)
        private val tvRatingBar : RatingBar? = itemView.findViewById(R.id.rating_bar)
        private val tvMovieOverview: TextView? = itemView.findViewById(R.id.tv_movie_overview)
        fun bind(movie: Movie, listener: TopRatedAdapterListener) {
            itemView.setOnClickListener {
                listener.onItemClicked(movie)
            }

            if (isLinearSwitched) {
                tvMovieName!!.text = movie.title
                tvMoviesYear!!.text = movie.releaseDate
                tvMovieOverview!!.text = movie.overview
                tvRatingBar!!.rating = (movie.voteAverage!!.toFloat() / 10) * 5
                Glide.with(itemView.context)
                    .load(URL_IMAGE + movie.posterPath)
                    .into(imgMovie!!)
            } else {
                tvMovieName!!.text = movie.title
                tvMoviesYear!!.text = movie.releaseDate
                tvRatingBar!!.rating = (movie.voteAverage!!.toFloat() / 10) * 5
                Glide.with(itemView.context)
                    .load(URL_IMAGE + movie.posterPath)
                    .into(imgMovie!!)
            }
        }

    }

    class RestaurantDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    interface TopRatedAdapterListener {
        fun onItemClicked(movie: Movie)
    }
}