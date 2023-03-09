package com.rysanek.timeclock.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rysanek.timeclock.R
import com.rysanek.timeclock.databinding.FragmentStartBinding
import com.rysanek.timeclock.ui.fragments.base.TimeClockFragment
import kotlinx.coroutines.delay

class StartFragment : TimeClockFragment() {

    private var _layout: FragmentStartBinding? = null
    private val layout get() = _layout!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _layout = FragmentStartBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenResumed {
            delay(2000)

            if (auth.currentUser != null){
                findNavController().navigate(R.id.action_startFragment_to_mainMenuFragment)
            } else {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }
        }

        return layout.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layout = null
    }



}