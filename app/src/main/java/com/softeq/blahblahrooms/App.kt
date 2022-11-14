package com.softeq.blahblahrooms

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application(), OnMapsSdkInitializedCallback {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Timber.d("The latest version of the renderer is used.")
            MapsInitializer.Renderer.LEGACY -> Timber.d("The legacy version of the renderer is used.")
        }
    }

}