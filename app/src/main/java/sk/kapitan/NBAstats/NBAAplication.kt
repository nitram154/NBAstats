package sk.kapitan.NBAstats

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NBAAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@NBAAplication)
            module {
                viewModel() {
                    MainViewModel()
                }
            }
        }
    }
}