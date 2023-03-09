package com.rysanek.timeclock.data.models

data class WorkShift(
    val clockInTime: Long?= null,
    var clockOutTime: Long? = null,
    var clockInImageUrl: String? = null,
    var clockOutImageUrl: String? = null
)