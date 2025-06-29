package kien.projects.focai.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kien.projects.focai.R
import kien.projects.focai.ui.theme.FocAITheme
import kien.projects.focai.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
object MainScreen

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    navToAppList: () -> Unit = {}
) {
    val context = LocalContext.current
    val startButtonState = mainViewModel.startButtonState.collectAsState()
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.size(200.dp),
                onClick = {
                    mainViewModel.onStartButtonClicked(context)
                }
            ) {
                when (startButtonState.value) {
                    false ->
                        Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.power_settings_new_90dp),
                        contentDescription = "Start Button"
                    )
                    true -> Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.stop_circle_90dp),
                        contentDescription = "Stop Button"
                    )
                }

            }
            Spacer(Modifier.size(10.dp))
            Button(onClick = {navToAppList()}) {
                Text(stringResource(R.string.app_list))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Preview
@Composable
fun MainScreenPreview() {
    FocAITheme {
        MainScreen(mainViewModel = viewModel())
    }
}