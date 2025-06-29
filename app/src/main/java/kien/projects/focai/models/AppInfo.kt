package kien.projects.focai.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap

data class AppInfo(
    val name: String,
    val icon: ImageBitmap,
    val packageName: String
){

    companion object{
        fun drawableToImageBitmap(context: Context, drawable: Drawable): ImageBitmap {
            val density = context.resources.displayMetrics.densityDpi
            val width = drawable.intrinsicWidth.coerceAtLeast(1)
            val height = drawable.intrinsicHeight.coerceAtLeast(1)

            // Tạo bitmap với config ARGB_8888 để giữ màu
            val bitmap = createBitmap(width, height)
            bitmap.density = density
            bitmap.setConfig(Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)

            // Bắt buộc phải clear tint nếu có
            drawable.colorFilter = null
            drawable.setTintList(null)
            drawable.draw(canvas)

            return bitmap.asImageBitmap()
        }


    }
}

