package com.thesis.android_challenge_w6.presentation.now_playing

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.api.movie.Movie
import com.thesis.android_challenge_w6.databinding.FragmentNowPlayingBinding
import com.thesis.android_challenge_w6.presentation.home.HomeFragment

class NowPlayingListFragment : Fragment() {
    private lateinit var nowPlayingListAdapter: NowPlayingListAdapter
    private lateinit var nowPlayingListViewModel: NowPlayingListViewModel
    private lateinit var binding: FragmentNowPlayingBinding
    private lateinit var menu: Menu
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel(inflater, container)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setHasOptionsMenu(true)
        fetchNowPlaying()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("NowPlaying", "onCreateOption")
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
        this.menu = menu
        if (nowPlayingListViewModel.isLinearSwitched.value!!) {
            menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_grid)
        } else {
            menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_linear)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_grid -> {
                nowPlayingListViewModel.isLinearSwitched.value =
                    nowPlayingListAdapter.toggleItemViewType()

                if (nowPlayingListViewModel.isLinearSwitched.value!!) {
                    binding.rvNowPlaying.layoutManager = LinearLayoutManager(activity)
                    menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_grid)
                } else {
                    binding.rvNowPlaying.layoutManager = GridLayoutManager(activity, 2)
                    menu[0].icon =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.ic_linear)
                }
                return true
            }
        }
        return false
    }

    private fun setupViewModel(inflater: LayoutInflater, container: ViewGroup?) {
        nowPlayingListViewModel = ViewModelProvider(this).get(NowPlayingListViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)
        binding.lifecycleOwner = this
        binding.nowPlayingViewModel = nowPlayingListViewModel
    }


    private fun setupRecyclerView() {
        Log.d("NowPlaying", "setupRecycler")
        nowPlayingListAdapter = NowPlayingListAdapter()
        nowPlayingListAdapter.isLinearSwitched = nowPlayingListViewModel.isLinearSwitched.value!!
        if (nowPlayingListAdapter.isLinearSwitched) {
            binding.rvNowPlaying.layoutManager = LinearLayoutManager(activity)
        } else {
            binding.rvNowPlaying.layoutManager = GridLayoutManager(activity, 2)

        }
        nowPlayingListAdapter.listener = object : NowPlayingListAdapter.NowPlayingAdapterListener {
            override fun onItemClicked(movie: Movie) {
                ViewCompat.postOnAnimationDelayed(view!!, // Delay to show ripple effect
                    Runnable {
                        val bundle = bundleOf("movie" to movie)
                        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                    }
                    ,50)

            }
        }
        binding.rvNowPlaying.adapter = nowPlayingListAdapter
    }

    private fun fetchNowPlaying() {
        nowPlayingListViewModel.getNowPlaying().observe(viewLifecycleOwner, Observer {
            activity?.runOnUiThread {
                nowPlayingListAdapter.submitList(it)
            }
        })
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}