package com.homo_sapiens.ecosync.util.extension

import com.homo_sapiens.ecosync.data.state.events.RemainingDate
import kotlin.math.floor

fun Long.formatDate(): RemainingDate {
    // get total seconds between the times
    var delta = this / 1000.0

    // calculate (and subtract) whole hours
    val hour = floor(delta / 3600).toInt()
    delta -= hour * 3600

    // calculate (and subtract) whole minutes
    val minute = (floor(delta / 60) % 60).toInt()
    delta -= minute * 60

    val second = delta.toInt()

    val hours = hour.numbersAsList().toMutableList()
    val minutes = minute.numbersAsList().toMutableList()
    val seconds = second.numbersAsList().toMutableList()

    if (2 - hours.count() >= 0)
        repeat(2 - hours.count()) {
            hours.add(0)
        }

    repeat(2 - minutes.count()) {
        minutes.add(0)
    }
    repeat(2 - seconds.count()) {
        seconds.add(0)
    }

    return RemainingDate(
        hour = hours.reversed(),
        minute = minutes.reversed(),
        second = seconds.reversed()
    )
}