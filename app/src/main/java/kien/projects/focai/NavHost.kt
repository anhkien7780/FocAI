package kien.projects.focai

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kien.projects.focai.ui.screens.AppListScreen
import kien.projects.focai.ui.screens.MainScreen
import kien.projects.focai.viewmodels.MainViewModel

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun FocAINavHost(){
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    mainViewModel.getLauncherApps(context)
    mainViewModel.getBlockPackageApps(context)

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