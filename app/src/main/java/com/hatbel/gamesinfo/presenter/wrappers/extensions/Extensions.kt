package com.hatbel.gamesinfo.presenter.wrappers.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(navDirections: NavDirections?) {
    navDirections?.let { directions ->
        currentDestination?.getAction(directions.actionId)?.run { navigate(directions) }
    }
}