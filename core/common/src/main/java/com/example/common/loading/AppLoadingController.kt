package com.example.common.loading

/**
 * Not Usage
 */

enum class AppLoadingController{
    LOADING,IDLE,SUCCESSES
}

sealed interface AppLoadingControllerWithType{
    class Loading(var type:String?) : AppLoadingControllerWithType
    class Idle(var type:String?): AppLoadingControllerWithType
}

object AppLoadingCheckType{
    fun checkLoadingType(loadingInterface: AppLoadingControllerWithType, loadingType:String):Boolean{
        return (loadingInterface is AppLoadingControllerWithType.Loading && (loadingInterface as AppLoadingControllerWithType.Loading).type==loadingType)
    }
}