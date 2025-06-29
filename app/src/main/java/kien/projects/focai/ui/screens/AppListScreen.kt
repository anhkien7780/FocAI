package kien.projects.focai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import kien.projects.focai.R
import kien.projects.focai.models.AppInfo
import kien.projects.focai.ui.theme.FocAITheme
import kien.projects.focai.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
object AppListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val launcherApps by mainViewModel.apps.collectAsState()
    val blockPackageApps by mainViewModel.blockPackageApps.collectAsState()


    Scaffold(modifier = modifier, topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(R.string.app_list))
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = launcherApps.count(), key = { it }) {
                AppItem(
                    appInfo = launcherApps[it],
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    checked = blockPackageApps.contains(launcherApps[it].packageName),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            mainViewModel.addBlockPackageApp(context, launcherApps[it].packageName)
                        } else {
                            mainViewModel.removeBlockPackageApp(
                                context,
                                launcherApps[it].packageName
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun AppItem(
    appInfo: AppInfo,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {

    var checked by remember { mutableStateOf(checked) }
    Row(
        modifier = modifier.defaultMinSize(
            minWidth = 300.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            bitmap = appInfo.icon,
            contentDescription = appInfo.name
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = appInfo.name,
            fontSize = 24.sp,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppItemPreview() {
    FocAITheme {
        val drawable =
            ContextCompat.getDrawable(LocalContext.current, R.drawable.account_circle_24dp)
        AppItem(
            appInfo = AppInfo(
                name = "Icon",
                icon = AppInfo.drawableToImageBitmap(LocalContext.current, drawable!!),
                packageName = ""
            )
        )
    }
}


@Preview
@Composable
fun AppListScreenPreview() {
    FocAITheme {
        AppListScreen(
            mainViewModel = viewModel()
        )
    }
}