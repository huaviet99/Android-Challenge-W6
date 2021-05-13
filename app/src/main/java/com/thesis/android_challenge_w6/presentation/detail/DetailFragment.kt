package com.thesis.android_challenge_w6.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.api.movie.Movie
import com.thesis.android_challenge_w6.presentation.main.MainActivity
import com.thesis.android_challenge_w6.presentation.now_playing.NowPlayingListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).appBarLayout?.visibility  = View.GONE
        val data = getDataFromBundle()
        data?.let {
            showDetailView(data)
        }
    }

    private fun showDetailView(data : Movie){
        tv_movie_name!!.text = data.title
        tv_overview_content!!.text = data.overview
        tv_censorship_value!!.text = if(data.adult!!) "Not for audiences under 18 years old"
        else "Available for all audiences"
        tv_popularity_value!!.text = data.popularity.toString()
        tv_release_date!!.text = data.releaseDate
        rating_bar!!.rating = (data.voteAverage!!.toFloat() / 10) * 5
        tv_average_vote_value!!.text = "(${data.voteAverage}/10)"
        txtVoteCountValue!!.text = data.voteCount.toString()
        Glide.with(this.requireContext())
            .load(NowPlayingListAdapter.URL_IMAGE + data.backdropPath)
            .into(img_backdrop_movie!!)

        Glide.with(this.requireContext())
            .load(NowPlayingListAdapter.URL_IMAGE + data.posterPath)
            .into(img_poster_movie!!)
    }

    private fun getDataFromBundle(): Movie? {
        return arguments?.getParcelable("movie")
    }


}