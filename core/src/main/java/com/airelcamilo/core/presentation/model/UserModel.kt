package com.airelcamilo.core.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val login: String,
    val avatarUrl: String,
    val isFavorite: Boolean
) : Parcelable
