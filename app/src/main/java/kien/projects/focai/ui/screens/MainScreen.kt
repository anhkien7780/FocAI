package kien.projects.focai.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kien.projects.focai.R
import kien.projects.focai.ui.theme.FocAITheme
import kien.projects.focai.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
object MainScreen

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    navToAppList: () -> Unit = {}
) {
    val startButtonState = mainViewModel.startButtonState.collectAsState()
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.size(200.dp),
                onClick = {
                    mainViewModel.onStartButtonClicked()
                }
            ) {
                when (startButtonState.value) {
                    true ->
                        Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.power_settings_new_90dp),
                        contentDescription = "Start Button"
                    )
                    false -> Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.stop_circle_90dp),
                        contentDescription = "Stop Button"
                    )
                }

            }
            Spacer(Modifier.size(10.dp))
            Button(onClick = {navToAppList()}) {
                Text("Danh sách ứng dụng")
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    FocAITheme {
        MainScreen(mainViewModel = viewModel())
    }
}