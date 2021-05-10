package com.thesis.android_challenge_w6.presentation.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.databinding.FragmentTopListBinding
import com.thesis.android_challenge_w6.model.Restaurant
import com.thesis.android_challenge_w6.presentation.user.UserFragment

class TopListFragment : Fragment() {
    private lateinit var topListAdapter: TopListAdapter
    private lateinit var topListViewModel: TopListViewModel
    private lateinit var binding: FragmentTopListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      setupViewModel(inflater,container)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        val userFragment = parentFragment as UserFragment
        val email = userFragment.getEmailFromBundle()
        topListViewModel.accessedEmail.value = email
        topListViewModel.fetchRestaurantList().observe(viewLifecycleOwner, Observer {
            activity?.runOnUiThread {
                topListAdapter.submitList(it)
            }
        })
    }

    private fun setupViewModel(inflater: LayoutInflater,container: ViewGroup?){
        topListViewModel = ViewModelProvider(this).get(TopListViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_list, container, false)
        binding.lifecycleOwner = this
        binding.topViewModel = topListViewModel
    }


    private fun setupRecyclerView() {
        topListAdapter = TopListAdapter()
        topListAdapter.listener = object : TopListAdapter.RestaurantAdapterListener {
            override fun onItemClicked(restaurant: Restaurant) {
                topListViewModel.toggleFavoriteRestaurant(restaurant)
            }
        }
        binding.rvRestaurantList.adapter = topListAdapter
    }


    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}