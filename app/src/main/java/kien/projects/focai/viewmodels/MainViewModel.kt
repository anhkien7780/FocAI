package kien.projects.focai.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kien.projects.focai.R
import kien.projects.focai.datastore.PackageAppNameStore
import kien.projects.focai.models.AppInfo
import kien.projects.focai.models.LoadingUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _loadingUIState: MutableStateFlow<LoadingUIState> =
        MutableStateFlow(LoadingUIState.Idle)
    private val _startButtonState = MutableStateFlow(false)
    private val _apps: MutableStateFlow<List<AppInfo>> = MutableStateFlow(emptyList())
    private val _blockPackageApps: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    val loadingUIState = _loadingUIState.asStateFlow()

    val apps = _apps.asStateFlow()
    val blockPackageApps = _blockPackageApps.asStateFlow()
    val startButtonState = _startButtonState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.M)
    fun onStartButtonClicked(context: Context) {
        if (Settings.canDrawOverlays(context)) {
            _startButtonState.value = !_startButtonState.value
        } else {
            checkOverlayPermission(context)
        }
    }

    fun getLauncherApps(context: Context) {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val apps = pm.queryIntentActivities(intent, 0)
        _apps.value = apps.map {
            AppInfo(
                name = it.loadLabel(pm).toString(),
                icon = AppInfo.drawableToImageBitmap(context = context, it.loadIcon(pm)),
                packageName = it.activityInfo.packageName
            )
        }
    }

    fun getBlockPackageApps(context: Context) {
        viewModelScope.launch {
            _blockPackageApps.value =
                PackageAppNameStore().getPackageAppNames(context) ?: emptyList()
        }
    }

    fun addBlockPackageApp(context: Context, packageName: String) {
        _blockPackageApps.value += packageName
        viewModelScope.launch {
            PackageAppNameStore().addPackageAppNames(context, _blockPackageApps.value)
        }
    }

    fun removeBlockPackageApp(context: Context, packageName: String) {
        _blockPackageApps.value -= packageName
        viewModelScope.launch {
            PackageAppNameStore().addPackageAppNames(context, _blockPackageApps.value)
        }
    }

    fun setIdle() {
        _loadingUIState.value = LoadingUIState.Idle
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkOverlayPermission(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.request_overlay_permission))
            .setMessage(context.getString(R.string.grant_overlay_permission_message))
            .setPositiveButton(context.getString(R.string.go_to_permission_manager)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    "package:${context.packageName}".toUri()
                )
                startActivityForResult(context as android.app.Activity, intent, 0, null)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}