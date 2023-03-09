package com.rysanek.timeclock.data.models

data class User(
    val name: String? = null,
    val email: String? = null,
    var clockInTime: Long? = null,
    var clockOuTime: Long? = null,
    val currentWorkShift: WorkShift?= null,
    val previousWorkShifts: List<WorkShift> = emptyList()
)