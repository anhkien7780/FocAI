package kien.projects.focai.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import kien.projects.focai.models.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel(){
    private val _startButtonState = MutableStateFlow(false)

    val startButtonState = _startButtonState

    fun onStartButtonClicked(){
        _startButtonState.value = !_startButtonState.value
    }

    fun getLauncherApps(context: Context): List<AppInfo>{
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

}