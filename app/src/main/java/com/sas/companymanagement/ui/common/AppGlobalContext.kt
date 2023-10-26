package com.sas.companymanagement.ui.common

import android.app.Application

class AppGlobalContext : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
    companion object {
        private  var appContext: AppGlobalContext? = null
        fun getAppContext() : AppGlobalContext{
            return  appContext  ?: run { return AppGlobalContext() }
        }
    }
}