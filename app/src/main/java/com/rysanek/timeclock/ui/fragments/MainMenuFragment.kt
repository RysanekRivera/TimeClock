package com.rysanek.timeclock.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rysanek.timeclock.R
import com.rysanek.timeclock.data.models.User
import com.rysanek.timeclock.data.models.UserObject.user
import com.rysanek.timeclock.databinding.FragmentMainMenuBinding
import com.rysanek.timeclock.domain.Utils.insertNewCurrentShift
import com.rysanek.timeclock.ui.fragments.base.TimeClockFragment

class MainMenuFragment : TimeClockFragment() {

    private var _layout: FragmentMainMenuBinding? = null
    private val layout get() = _layout!!
    companion object { const val TAG = "MainMenuFragment" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _layout = FragmentMainMenuBinding.inflate(inflater, container, false)

        with(layout) {
            btClockInOut.setOnClickListener {
                findNavController().navigate(R.id.action_mainMenuFragment_to_clockInOutActivity)
            }

            btLogOut.setOnClickListener {
                auth.signOut()
                findNavController().navigate(R.id.action_mainMenuFragment_to_singInSignUpFragment)
            }
        }

        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            cloudDb.collection("Users").document(auth.uid!!)
                .get()
                .addOnSuccessListener { document ->
                    document.toObject(User::class.java)?.let { userObject ->
                        Log.d(TAG, userObject.toString())
                        user = userObject
                        if (user?.currentWorkShift == null) {
                            layout.clClockedIn.visibility = View.GONE
                        } else {
                            user?.currentWorkShift?.clockInTime?.let {
                                layout.clClockedIn.visibility = View.VISIBLE
                                layout.tvClockInTimeValue.text = it.toString() ?: ""
                            }

                        }

                    }
                }
        } catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
        }

    }

}