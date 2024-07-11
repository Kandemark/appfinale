package com.application.myapplication.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestinations {
    val route: String
}
object Dashboard : AppDestinations {
    override val route = "dashboard"
}
//
//object ProductDetail : AppDestinations {
//    override val route = "productDetail"
//
//    private const val PRODUCT_ID = "id"
//    val routeWithArgs = "$route/{$PRODUCT_ID}"
//    val arguments = listOf(
//        navArgument(PRODUCT_ID) { type = NavType.IntType },
//    )
//}


object Profile : AppDestinations {
    override val route = "profile"
}

object SignIn : AppDestinations {
    override val route = "signIn"
}

object SignUp : AppDestinations {
    override val route = "signUp"
}

object ForgotPass: AppDestinations {
    override val route = "forgotPass"
}


object Payment : AppDestinations {
    override val route = "payment"
}

object Notification: AppDestinations {
    override val route = "notification"
}

object Otp : AppDestinations {
    override val route = "otp"
}

