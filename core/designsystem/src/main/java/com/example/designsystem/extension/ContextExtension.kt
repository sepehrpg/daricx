package com.example.designsystem.extension
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.core.app.ActivityCompat



fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}



fun Activity.shouldShowRationale(permissions: List<String>) =
    permissions.any { permission ->
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    }
