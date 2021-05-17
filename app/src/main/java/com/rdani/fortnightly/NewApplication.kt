package com.rdani.fortnightly

import android.app.Application
import com.rdani.fortnightly.api.ApiModule
import com.rdani.fortnightly.feature.ViewModelModule
import com.rdani.fortnightly.repository.RepositoryModule
import com.rdani.fortnightly.services.ServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewApplication)
            modules(
                ApiModule.module,
                ServiceModule.module,
                RepositoryModule.module,
                ViewModelModule.module,
            )
        }
    }
}