package com.thesis.android_challenge_w6.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thesis.android_challenge_w6.R
import com.thesis.android_challenge_w6.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(){
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupViewModel(inflater,container)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignIn.setOnClickListener {
               findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
        }

        viewModel.isSignUpSucceed.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    showToastMessage("Sign Up Successful")
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)

                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                showToastMessage(message)
            }
        })
    }

    private fun setupViewModel(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false)
        binding.lifecycleOwner = this
        binding.signUpViewModel = viewModel
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}