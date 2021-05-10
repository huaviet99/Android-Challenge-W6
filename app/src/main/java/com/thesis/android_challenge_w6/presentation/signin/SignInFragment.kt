package com.thesis.android_challenge_w6.presentation.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupViewModel(inflater,container)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
        viewModel.isSignInSucceed.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                showToastMessage("Sign in Successful")
                val bundle = bundleOf("email" to user.email)
                findNavController().navigate(R.id.action_signInFragment_to_userFragment,bundle)
            }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                showToastMessage(message)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.clear()
    }

    private fun setupViewModel(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = this
        binding.signInViewModel = viewModel
    }


    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}