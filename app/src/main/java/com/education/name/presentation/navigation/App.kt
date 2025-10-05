package com.education.name.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.education.name.presentation.ui.screen.auth.LoginScreen
import com.education.name.presentation.ui.screen.auth.SignupScreen

object Routes {
    const val SIGNUP = "signup"
    const val LOGIN = "login"
    const val HOME = "home"
}

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGNUP) { inclusive = true } // remove signup from backstack
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true } // remove login from backstack
                    }
                }
            )
        }

        composable(Routes.HOME) {
            // Replace with your HomeScreen
            androidx.compose.material3.Text("Home Screen")
        }
    }
}
