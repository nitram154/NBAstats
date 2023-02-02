package sk.kapitan.NBAstats

import android.app.Application
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sk.kapitan.NBAstats.data.NBARepository
import sk.kapitan.NBAstats.data.NBAService

class NBAAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@NBAAplication)
            modules(listOf(module {
                viewModel() {
                    MainViewModel(get())
                }
                single {
                    Retrofit.Builder()
                        .baseUrl("https://www.balldontlie.io")
                        .client(OkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(Gson()))
                        .build()
                        .run { create(NBAService::class.java) }
                }
                single {
                    NBARepository(get())
                }
                viewModel() { (teamId: Int) ->

                    GamesViewModel(teamId, get())

                }
            }))
        }
    }
}