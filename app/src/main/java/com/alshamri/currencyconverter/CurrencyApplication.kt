package com.alshamri.currencyconverter

import android.app.Application
import com.alshamri.currencyconverter.di.AppModule
import com.alshamri.currencyconverter.di.AppModuleImpl
import com.alshamri.currencyconverter.util.ThemeManager

class CurrencyApplication : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
        ThemeManager(this).applyTheme()
    }
}
