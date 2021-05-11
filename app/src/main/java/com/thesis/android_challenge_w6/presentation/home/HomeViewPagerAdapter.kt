package com.thesis.android_challenge_w6.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.thesis.android_challenge_w6.presentation.favorite.TopRatedListFragment
import com.thesis.android_challenge_w6.presentation.now_playing.NowPlayingListFragment


class HomeViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragmentList = listOf<Fragment>(NowPlayingListFragment(),TopRatedListFragment())
    companion object {
        const val TOP_PAGE = 0
        const val FAVORITE_PAGE = 1

        const val MAX_PAGES = 2
    }

    override fun getItem(position: Int): Fragment {
       return fragmentList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return MAX_PAGES
    }
}