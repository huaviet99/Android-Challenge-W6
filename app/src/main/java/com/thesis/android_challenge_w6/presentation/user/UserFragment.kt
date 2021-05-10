package com.thesis.android_challenge_w6.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.thesis.android_challenge_w6.R
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigationView()
        setupViewPager()

    }

    fun getEmailFromBundle(): String? {
        return arguments?.getString("email")
    }

    private fun setupBottomNavigationView() {
        bottom_nav_user.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_top -> {
                    user_view_pager.currentItem = UserViewPagerAdapter.TOP_PAGE
                    true

                }
                R.id.item_favorite -> {
                    user_view_pager.currentItem = UserViewPagerAdapter.FAVORITE_PAGE
                    true
                }
                else -> false
            }
        }
    }

    private fun setupViewPager() {
        toolbar.title = "Top Restaurant"
        val mainViewPagerAdapter = UserViewPagerAdapter(childFragmentManager)
        user_view_pager.adapter = mainViewPagerAdapter
        user_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    UserViewPagerAdapter.TOP_PAGE -> {
                        bottom_nav_user.menu.findItem(R.id.item_top).isChecked = true
                        toolbar.title = "Top Restaurant"

                    }
                    UserViewPagerAdapter.FAVORITE_PAGE -> {
                        bottom_nav_user.menu.findItem(R.id.item_favorite).isChecked = true
                        toolbar.title = "Favorite Restaurant"
                        activity!!.runOnUiThread { mainViewPagerAdapter.notifyDataSetChanged() }

                    }

                }
            }
        })
    }
}