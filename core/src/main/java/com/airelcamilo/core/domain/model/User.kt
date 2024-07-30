package com.airelcamilo.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String,
    val avatarUrl: String,
    val isFavorite: Boolean
) : Parcelable
