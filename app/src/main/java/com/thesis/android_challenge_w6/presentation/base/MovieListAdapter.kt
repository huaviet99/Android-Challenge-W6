package com.thesis.android_challenge_w6.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.api.movie.Movie

class MovieListAdapter :
    ListAdapter<Movie, MovieListAdapter.ViewHolder>(RestaurantDiffUtilCallback()) {
    companion object {
        const val LINEAR_ITEM = 0
        const val GRID_ITEM = 1
    }

    var isLinearSwitched = true
    var listener: MovieItemListener? = null

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

        fun bind(movie: Movie, listener: MovieItemListener) {
            itemView.setOnClickListener {
                listener.onItemClicked(movie)
            }


            tvMovieName!!.text = movie.title
            tvMoviesYear!!.text = movie.releaseDate
            tvRatingBar!!.rating = (movie.voteAverage!!.toFloat() / 10) * 5
            GlideUtils.loadImage(itemView.context,movie.posterPath!!,imgMovie!!)


            if (isLinearSwitched) {
                tvMovieOverview!!.text = movie.overview
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

    interface MovieItemListener {
        fun onItemClicked(movie: Movie)
    }
}