package com.thesis.android_challenge_w6.presentation.favorite

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
import com.thesis.android_challenge_w6.databinding.FragmentTopRatedBinding
import com.thesis.android_challenge_w6.presentation.home.HomeFragment

class TopRatedListFragment : Fragment() {
    private lateinit var topRatedListAdapter: TopRatedListAdapter
    private lateinit var topRatedListViewModel: TopRatedListViewModel
    private lateinit var binding: FragmentTopRatedBinding
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
        Log.d("FavoriteLs", "onViewCreated")
        setupRecyclerView()
        setHasOptionsMenu(true)
        topRatedListViewModel.getTopRated().observe(viewLifecycleOwner, Observer {
            activity?.runOnUiThread {
                topRatedListAdapter.submitList(it)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
        this.menu = menu

        if (topRatedListViewModel.isLinearSwitched.value!!) {
            menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_grid)
        } else {
            menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_linear)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_grid -> {
                topRatedListViewModel.isLinearSwitched.value =
                    topRatedListAdapter.toggleItemViewType()

                if (topRatedListViewModel.isLinearSwitched.value!!) {
                    binding.rvTopRated.layoutManager = LinearLayoutManager(activity)
                    menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_grid)
                } else {
                    binding.rvTopRated.layoutManager = GridLayoutManager(activity, 2)
                    menu[0].icon =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.ic_linear)
                }
                return true
            }
        }
        return false
    }


    private fun setupViewModel(inflater: LayoutInflater, container: ViewGroup?) {
        topRatedListViewModel = ViewModelProvider(this).get(TopRatedListViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated, container, false)
        binding.lifecycleOwner = this
        binding.topRatedListViewModel = topRatedListViewModel
    }

    private fun setupRecyclerView() {
        topRatedListAdapter = TopRatedListAdapter()
        topRatedListAdapter.isLinearSwitched = topRatedListViewModel.isLinearSwitched.value!!
        if (topRatedListAdapter.isLinearSwitched) {
            binding.rvTopRated.layoutManager = LinearLayoutManager(activity)
        } else {
            binding.rvTopRated.layoutManager = GridLayoutManager(activity, 2)

        }

        topRatedListAdapter.listener = object : TopRatedListAdapter.TopRatedAdapterListener {
            override fun onItemClicked(movie: Movie) {
                ViewCompat.postOnAnimationDelayed(view!!, // Delay to show ripple effect
                    Runnable {
                        val bundle = bundleOf("movie" to movie)
                        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                    }
                    ,50)

            }
        }
        binding.rvTopRated.adapter = topRatedListAdapter
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}