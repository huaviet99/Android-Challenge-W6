package com.thesis.android_challenge_w6.presentation.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).appBarLayout?.visibility  = View.VISIBLE
        setupBottomNavigationView()
        setupViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).appBarLayout?.visibility  = View.GONE
    }


    fun getEmailFromBundle(): String? {
        return arguments?.getString("email")
    }

    private fun setupBottomNavigationView() {
        bottom_nav_home.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_now_playing -> {
                    home_view_pager.currentItem = HomeViewPagerAdapter.TOP_PAGE
                    true

                }
                R.id.item_top_rated -> {
                    home_view_pager.currentItem = HomeViewPagerAdapter.FAVORITE_PAGE
                    true
                }
                else -> false
            }
        }
    }

    private fun setupViewPager() {
        (activity as MainActivity).supportActionBar?.title  = "Now Playing"
        val mainViewPagerAdapter = HomeViewPagerAdapter(childFragmentManager)
        home_view_pager.adapter = mainViewPagerAdapter
        home_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    HomeViewPagerAdapter.TOP_PAGE -> {
                        bottom_nav_home.menu.findItem(R.id.item_now_playing).isChecked = true
                        (activity as MainActivity).supportActionBar?.title  = "Now Playing"
                    }
                    HomeViewPagerAdapter.FAVORITE_PAGE -> {
                        bottom_nav_home.menu.findItem(R.id.item_top_rated).isChecked = true
                        (activity as MainActivity).supportActionBar?.title  = "Top Rated"
                    }

                }
            }
        })
    }
}