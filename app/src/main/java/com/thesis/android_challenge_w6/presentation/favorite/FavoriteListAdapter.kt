package com.thesis.android_challenge_w6.presentation.favorite

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.model.Restaurant

class FavoriteListAdapter : ListAdapter<Restaurant, FavoriteListAdapter.ViewHolder>(RestaurantDiffUtilCallback()) {
    companion object {
        const val LINEAR_ITEM = 0
        const val GRID_ITEM = 1
    }

    private var isLinearSwitched = true
    var listener : RestaurantAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View?
        view = if(viewType == LINEAR_ITEM) {
            inflater.inflate(R.layout.item_linear_restaurant, parent, false)
        } else  {
            inflater.inflate(R.layout.item_grid_restaurant, parent, false)
        }
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = getItem(position)
        holder.bind(item,listener)
    }

    override fun getItemViewType(position: Int): Int {
        return if(isLinearSwitched){
            LINEAR_ITEM
        } else {
            GRID_ITEM
        }
    }

    fun toggleItemViewType(): Boolean{
        isLinearSwitched = !isLinearSwitched
        return isLinearSwitched
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRestaurantName: TextView = itemView.findViewById(R.id.tv_restaurant_name)
        private val tvRestaurantAddress: TextView = itemView.findViewById(R.id.tv_restaurant_address)
        private val imgRestaurant: ImageView = itemView.findViewById(R.id.img_restaurant)
         private val imgFavoriteCheck: ImageView = itemView.findViewById(R.id.img_favorite_check)
         private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        fun bind(restaurant: Restaurant, listener: RestaurantAdapterListener?) {
            imgFavoriteCheck.visibility = View.GONE
            tvRestaurantName.text = restaurant.name
            tvRestaurantAddress.text = restaurant.address
            Glide.with(itemView.context)
                .load(restaurant.picturePath)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(imgRestaurant)


            imgFavoriteCheck.setOnClickListener {
                listener?.onItemClicked(restaurant)
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

    interface RestaurantAdapterListener {
        fun onItemClicked(restaurant: Restaurant)
    }

}