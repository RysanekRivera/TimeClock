package com.rysanek.timeclock.ui.fragments.base

import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

abstract class TimeClockFragment: Fragment() {

    protected val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    protected val cloudDb = Firebase.firestore

}