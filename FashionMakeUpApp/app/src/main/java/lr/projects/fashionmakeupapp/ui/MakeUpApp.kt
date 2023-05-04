package lr.projects.fashionmakeupapp.ui

import android.window.SplashScreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import lr.projects.fashionmakeupapp.model.Product
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpDetailsScreen
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpDetailsViewModel
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpListScreen
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpListViewModel


@Composable
fun MakeUpApp(snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MakeUpProducts") {
        composable("MakeUpProducts") {
            MakeUpListScreen(navController, snackbarHostState)
        }
        composable(
            "Detail/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val itemId = navBackStackEntry.arguments?.getString("itemId")
            MakeUpDetailsScreen(navController, snackbarHostState,itemId)
        }
    }
}