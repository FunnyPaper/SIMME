package com.funnypaper.simme.domain.extensions

import java.time.LocalTime

fun LocalTime.toMillis() = (toNanoOfDay() * .000001).toLong()