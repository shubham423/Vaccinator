package com.example.vaccinator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VaccinatorApp: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: VaccinatorApp? = null
        fun applicationContext(): VaccinatorApp {
            return instance as VaccinatorApp
        }
    }
}