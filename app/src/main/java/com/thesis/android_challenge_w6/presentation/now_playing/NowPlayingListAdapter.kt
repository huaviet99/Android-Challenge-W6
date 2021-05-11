package com.thesis.android_challenge_w6.presentation.now_playing

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
import com.thesis.android_challenge_w6.presentation.favorite.TopRatedListAdapter

class NowPlayingListAdapter :
    ListAdapter<Restaurant, NowPlayingListAdapter.ViewHolder>(RestaurantDiffUtilCallback()) {
    companion object {
        const val LINEAR_ITEM = 0
        const val GRID_ITEM = 1
    }

    var listener: NowPlayingAdapterListener? = null

    private var isLinearSwitched = true

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
        fun bind(restaurant: Restaurant, listener: NowPlayingAdapterListener) {
            itemView.setOnClickListener {
                listener.onItemClicked(restaurant)
            }

            if (isLinearSwitched) {
                tvMovieName!!.text = restaurant.name
                tvMovieGenre!!.text = restaurant.address
                Glide.with(itemView.context)
                    .load(restaurant.picturePath)
                    .into(imgMovie!!)
            } else {
                tvMovieName!!.text = restaurant.name
                Glide.with(itemView.context)
                    .load(restaurant.picturePath)
                    .into(imgMovie!!)
            }

        }
    }

    class RestaurantDiffUtilCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }

    interface NowPlayingAdapterListener {
        fun onItemClicked(restaurant: Restaurant)
    }
}