package com.softeq.blahblahrooms.data.providers

import android.content.res.Resources
import android.util.Patterns
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Room
import java.text.NumberFormat
import java.util.*

fun Room.getMarkerTitle(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(price)
}

fun Room.getPriceUSFormat(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(price)
}

fun Room.getSnippet(): String {
    return this.description
}

fun Room.isValid(resources: Resources): String? {
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return resources.getString(R.string.incorrect_email)
    }
    if (description.isBlank()) {
        return resources.getString(R.string.empty_desc)
    }
    if (address.isBlank()) {
        return resources.getString(R.string.empty_address)
    }
    if (price < 0) {
        return resources.getString(R.string.incorrect_price)
    }
    return null
}