package com.thesis.android_challenge_w6.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.api.movie.Movie
import com.thesis.android_challenge_w6.databinding.FragmentDetailBinding
import com.thesis.android_challenge_w6.databinding.FragmentSignInBinding
import com.thesis.android_challenge_w6.presentation.base.GlideUtils
import com.thesis.android_challenge_w6.presentation.base.MOVIE_IMAGE_URL
import com.thesis.android_challenge_w6.presentation.main.MainActivity
import com.thesis.android_challenge_w6.presentation.signin.SignInViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupViewModel(inflater,container)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).appBarLayout?.visibility  = View.GONE
        val data = getDataFromBundle()
        data?.let {
            viewModel.movieData.value = data
            showDetailView()
        }
    }

    private fun setupViewModel(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        binding.detailViewModel = viewModel
    }

    private fun showDetailView(){
        binding.apply {
            tvMovieName.text = viewModel.movieData.value!!.title
            tvOverviewContent.text = viewModel.movieData.value!!.overview
            tvCensorshipValue.text = if(viewModel.movieData.value!!.adult!!) "Not for audiences under 18 years old"
            else "Available for all audiences"
            tvPopularityValue.text = viewModel.movieData.value!!.popularity.toString()
            tvReleaseDate.text = viewModel.movieData.value!!.releaseDate
            ratingBar.rating = (viewModel.movieData.value!!.voteAverage!!.toFloat() / 2)
            tvAverageVoteValue.text = "(${viewModel.movieData.value!!.voteAverage}/10)"
            tvVoteCountValue.text = viewModel.movieData.value!!.voteCount.toString()
            GlideUtils.loadImage((this@DetailFragment.requireContext()),viewModel.movieData.value!!.backdropPath!!,img_backdrop_movie!!)
            GlideUtils.loadImage((this@DetailFragment.requireContext()), viewModel.movieData.value!!.posterPath!!,img_poster_movie!!)

        }

    }

    private fun getDataFromBundle(): Movie? {
        return arguments?.getParcelable("movie")
    }


}