package com.rysanek.timeclock.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rysanek.timeclock.R
import com.rysanek.timeclock.databinding.FragmentSignInSignUpBinding
import com.rysanek.timeclock.ui.fragments.base.TimeClockFragment

class SignInSignUpFragment : TimeClockFragment() {

    private var _layout: FragmentSignInSignUpBinding? = null
    private val layout get() = _layout!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _layout = FragmentSignInSignUpBinding.inflate(inflater, container, false)

        layout.btLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_singInSignUpFragment_to_loginFragment)
        }

        return layout.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layout = null
    }

}