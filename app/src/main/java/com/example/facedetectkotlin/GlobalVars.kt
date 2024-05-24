package com.example.facedetectkotlin

import android.app.Application

class GlobalVars: Application() {

    companion object{
        var distanceMultiplier = 0.65
        var distance = 30
        var spinnerDistanceItems = arrayOf("20", "30", "40", "50", "60", "70", "80")
        var cameraValuesRounding = 3.0
        lateinit var cameraResults: FaceLandmarkerHelper.ResultBundle
    }
}