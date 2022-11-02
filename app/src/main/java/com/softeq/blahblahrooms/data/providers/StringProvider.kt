package com.softeq.blahblahrooms.data.providers

import android.content.res.Resources
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import java.text.NumberFormat
import java.util.*

fun Period.getString(resources: Resources): String {
    return when (this) {
        Period.LONG -> resources.getString(R.string.long_term)
        Period.SHORT -> resources.getString(R.string.short_term)
    }
}

fun Room.getTitle(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(price)
}

fun Room.getSnippet(): String {
    return this.description
}