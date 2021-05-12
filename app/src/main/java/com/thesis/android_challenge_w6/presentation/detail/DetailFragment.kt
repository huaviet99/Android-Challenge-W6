package com.thesis.android_challenge_w6.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.api.movie.Movie
import com.thesis.android_challenge_w6.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*


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
            Log.d("Test",data.toString())
        }
    }

    private fun getDataFromBundle(): Movie? {
        return arguments?.getParcelable("movies")
    }


}