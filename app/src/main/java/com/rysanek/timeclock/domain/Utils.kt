package com.rysanek.timeclock.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object Utils {

    fun updateCurrentShift(
        clockInTime: Long? = null,
        clockInImageUrl: String? = null,
        clockOuTime: Long? = null,
        clockOutImageUrl: String? = null
    ) {
        val doc = FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION).document(FirebaseAuth.getInstance().uid!!)

        clockInTime?.let { doc.update(mapOf("${Constants.CURRENT_WORK_SHIFT}.${Constants.CLOCK_IN_TIME}" to clockInTime)) }
        clockInImageUrl?.let { doc.update(mapOf("${Constants.CURRENT_WORK_SHIFT}.${Constants.CLOCK_IN_IMAGE_URL}" to clockInImageUrl)) }
        clockOuTime?.let { doc.update(mapOf("${Constants.CURRENT_WORK_SHIFT}.${Constants.CLOCK_OUT_TIME}" to clockOuTime)) }
        clockOutImageUrl?.let { doc.update(mapOf("${Constants.CURRENT_WORK_SHIFT}.${Constants.CLOCK_OUT_IMAGE_URL}" to clockOutImageUrl)) }

    }

    fun nullifyCurrentShift() {
        val doc = FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION).document(FirebaseAuth.getInstance().uid!!)

        val updates: MutableMap<String, Any> = HashMap()
        updates[Constants.CURRENT_WORK_SHIFT] = FieldValue.delete()

        doc.update(updates)

    }

    fun insertNewCurrentShift() {
        val doc = FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION).document(FirebaseAuth.getInstance().uid!!)

        val subFields: MutableMap<String, Any?> = HashMap()
        subFields[Constants.CLOCK_IN_TIME] = System.currentTimeMillis()
        subFields[Constants.CLOCK_OUT_TIME] = null
        subFields[Constants.CLOCK_IN_IMAGE_URL] = null
        subFields[Constants.CLOCK_OUT_IMAGE_URL] = null

        val currentShift: MutableMap<String, Any> = HashMap()
        currentShift[Constants.CURRENT_WORK_SHIFT] = subFields

        doc.update(currentShift)

    }

}