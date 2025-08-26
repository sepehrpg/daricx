package com.example.common.log


/**
 * Deprecated : Use Timber
 */

import android.util.Log

enum class AppLogType {
    I,E,V,D
}

object AppLogController {
    fun showLog(name:String = AppTagName.TAG_APPLICATION_LOG, value:List<Any>, type:AppLogType? = null){
        value.forEach {
            when (type){
                AppLogType.D -> Log.d(name,it.toString())
                AppLogType.I -> Log.i(name,it.toString())
                AppLogType.E -> Log.e(name,it.toString())
                AppLogType.V -> Log.v(name,it.toString())
                else -> Log.d(name,it.toString())
            }
        }
    }

}


