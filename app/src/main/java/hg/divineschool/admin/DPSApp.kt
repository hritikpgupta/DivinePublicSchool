package hg.divineschool.admin

import android.app.Application
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig
import androidx.camera.core.ExperimentalAvailableCamerasLimiter
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
//class DPSApp :Application(), CameraXConfig.Provider{
//    @OptIn(ExperimentalAvailableCamerasLimiter::class, androidx.camera.core.ExperimentalLogging::class )
//    override fun getCameraXConfig(): CameraXConfig {
//        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
//            .setAvailableCamerasLimiter(CameraSelector.DEFAULT_BACK_CAMERA)
//            .setMinimumLoggingLevel(Log.ERROR)
//            .build()
//    }
//
//}

@HiltAndroidApp
class DPSApp :Application()