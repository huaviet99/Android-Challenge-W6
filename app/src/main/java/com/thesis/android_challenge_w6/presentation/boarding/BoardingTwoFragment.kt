package com.thesis.android_challenge_w6.presentation.boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thesis.android_challenge_w6.R
import kotlinx.android.synthetic.main.fragment_onboarding_two.*

class BoardingTwoFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_two,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_next.setOnClickListener {
            findNavController().navigate(R.id.action_boardingTwoFragment_to_boardingThreeFragment)
        }
    }
}