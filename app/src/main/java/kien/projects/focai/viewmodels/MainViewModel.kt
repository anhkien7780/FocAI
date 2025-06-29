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
import kien.projects.focai.R
import kien.projects.focai.models.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
    private val _startButtonState = MutableStateFlow(false)

    val startButtonState = _startButtonState

    @RequiresApi(Build.VERSION_CODES.M)
    fun onStartButtonClicked(context: Context) {
        if (Settings.canDrawOverlays(context)) {
            _startButtonState.value = !_startButtonState.value
        } else {
            checkOverlayPermission(context)
        }
    }

    fun getLauncherApps(context: Context): List<AppInfo> {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val apps = pm.queryIntentActivities(intent, 0)
        return apps.map {
            AppInfo(
                name = it.loadLabel(pm).toString(),
                icon = AppInfo.drawableToImageBitmap(context = context, it.loadIcon(pm))
            )
        }
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