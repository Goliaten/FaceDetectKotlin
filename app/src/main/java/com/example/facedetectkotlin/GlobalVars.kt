package com.example.facedetectkotlin

import android.app.Application

class GlobalVars: Application() {

    companion object{
        val defaultMultiplier = arrayOf(0.65, 1.0)
        val defaultDistance = arrayOf(30.0, 1.0)
        // TODO: Zapisywanie konfiguracji i reset wszystkich ustawień na raz
        var distanceMultiplier = defaultMultiplier.clone()
        var distance = defaultDistance.clone()
        var cameraValuesRounding = 2.0
        lateinit var cameraResults: FaceLandmarkerHelper.ResultBundle
        // TODO: lista distansów i iloczynów, więcej algorytmów kamery
        var cameraAlgorithmIndex = 0
    }
}