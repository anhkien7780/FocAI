package kien.projects.focai

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kien.projects.focai.ui.screens.AppListScreen
import kien.projects.focai.ui.screens.MainScreen
import kien.projects.focai.viewmodels.MainViewModel

@Composable
fun FocAINavHost(){
    val mainViewModel: MainViewModel = viewModel()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreen){
        composable<MainScreen> {
            MainScreen(mainViewModel = mainViewModel, navToAppList = {
                navController.navigate(AppListScreen)
            })
        }
        composable<AppListScreen> {
            AppListScreen(mainViewModel = mainViewModel) {
                navController.popBackStack()
            }
        }

    }

}