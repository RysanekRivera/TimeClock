package com.rysanek.timeclock.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.rysanek.timeclock.R
import com.rysanek.timeclock.databinding.FragmentLoginBinding
import com.rysanek.timeclock.ui.fragments.base.TimeClockFragment

class LoginFragment : TimeClockFragment() {

    private var _layout: FragmentLoginBinding? = null
    private val layout get() = _layout!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _layout = FragmentLoginBinding.inflate(inflater, container, false)

        with(layout){

            btLogIn.setOnClickListener {
                if (!edtEmail.text.isNullOrEmpty() && !edtPassword.text.isNullOrEmpty()) {
                    loginWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString())
                }
            }
        }

        return layout.root
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful){
                            findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment, null, NavOptions.Builder().setPopUpTo(R.id.singInSignUpFragment, true).build())
                        } else {
                            Toast.makeText(requireContext(), task.exception?.message ?: "An Unknown Error Occurred",Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Log.d("Main", "Error: ${e.message}")
                        Toast.makeText(requireContext(), task.exception?.message ?: e.message ?: "An Unknown Error Occurred",Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Log.d("Main", "Error: ${e.message}")
        }

    }

}