package com.thesis.android_challenge_w6.presentation.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.databinding.FragmentFavoriteListBinding
import com.thesis.android_challenge_w6.presentation.user.UserFragment

class FavoriteListFragment : Fragment() {
    private lateinit var favoriteListAdapter: FavoriteListAdapter
    private lateinit var favoriteListViewModel: FavoriteListViewModel
    private lateinit var binding: FragmentFavoriteListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FavoriteLs","onCreateView")
        setupViewModel(inflater, container)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FavoriteLs","onViewCreated")

        setupRecyclerView()
        val userFragment = parentFragment as UserFragment
        val email = userFragment.getEmailFromBundle()
        favoriteListViewModel.accessedEmail.value = email
        favoriteListViewModel.fetchRestaurantList().observe(viewLifecycleOwner, Observer {
            activity?.runOnUiThread {
                favoriteListAdapter.submitList(it)
            }
        })
    }

    private fun setupViewModel(inflater: LayoutInflater, container: ViewGroup?) {
        favoriteListViewModel = ViewModelProvider(this).get(FavoriteListViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_list, container, false)
        binding.lifecycleOwner = this
        binding.favoriteViewModel = favoriteListViewModel
    }

    private fun setupRecyclerView() {
        favoriteListAdapter = FavoriteListAdapter()
        binding.rvFavoriteList.adapter = favoriteListAdapter
    }
}