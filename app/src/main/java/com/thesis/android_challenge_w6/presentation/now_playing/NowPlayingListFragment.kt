package com.thesis.android_challenge_w6.presentation.now_playing

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.android_challenge_w6.R
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
        val userFragment = parentFragment as HomeFragment
        val email = userFragment.getEmailFromBundle()
        nowPlayingListViewModel.accessedEmail.value = email
        nowPlayingListViewModel.fetchRestaurantList().observe(viewLifecycleOwner, Observer {
            activity?.runOnUiThread {
                nowPlayingListAdapter.submitList(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_grid -> {
                val isLinearSwitched: Boolean = nowPlayingListAdapter.toggleItemViewType()

                if (isLinearSwitched) {
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
        nowPlayingListAdapter = NowPlayingListAdapter()
        binding.rvNowPlaying.adapter = nowPlayingListAdapter
    }


    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}