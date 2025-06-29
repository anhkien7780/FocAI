package kien.projects.focai.viewmodels

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
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
            Toast.makeText(
                context,
                context.getString(R.string.please_grant_ovelay_permission),
                Toast.LENGTH_LONG
            ).show()
            Handler(Looper.getMainLooper()).postDelayed({
                checkOverlayPermission(context)
            }, 2000)

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
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        context.startActivity(intent)
    }
}