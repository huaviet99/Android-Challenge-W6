package com.thesis.android_challenge_w6.presentation.favorite

import android.os.Bundle
import android.util.Log
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
import com.thesis.android_challenge_w6.databinding.FragmentFavoriteListBinding
import com.thesis.android_challenge_w6.presentation.user.UserFragment

class FavoriteListFragment : Fragment() {
    private lateinit var favoriteListAdapter: FavoriteListAdapter
    private lateinit var favoriteListViewModel: FavoriteListViewModel
    private lateinit var binding: FragmentFavoriteListBinding

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
        val userFragment = parentFragment as UserFragment
        val email = userFragment.getEmailFromBundle()
        favoriteListViewModel.accessedEmail.value = email
        favoriteListViewModel.fetchRestaurantList().observe(viewLifecycleOwner, Observer {
            activity?.runOnUiThread {
                favoriteListAdapter.submitList(it)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user, menu)
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_grid -> {
                showToastMessage("Favorite Clicked")
                val isLinearSwitched: Boolean = favoriteListAdapter.toggleItemViewType()

                if (isLinearSwitched) {
                    binding.rvFavoriteList.layoutManager = LinearLayoutManager(activity)
                    menu[0].icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_grid)
                } else {
                    binding.rvFavoriteList.layoutManager = GridLayoutManager(activity, 2)
                    menu[0].icon =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.ic_linear)
                }
                return true
            }
        }
        return false
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

    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}