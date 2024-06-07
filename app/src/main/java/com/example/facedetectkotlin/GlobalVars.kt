package com.example.facedetectkotlin

import android.app.Application

class GlobalVars: Application() {

    companion object{
        // TODO: Zapisywanie konfiguracji i reset wszystkich ustawień na raz
        var distanceMultiplier = 0.65
        var distance = 30.0
        var cameraValuesRounding = 3.0
        lateinit var cameraResults: FaceLandmarkerHelper.ResultBundle
        // TODO: lista distansów i iloczynów, więcej algorytmów kamery
        const val defaultMultiplier = 0.65
        const val defaultDistance = 30.0
        var cameraAlgorithmIndex = 0
    }
}