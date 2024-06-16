package com.example.facedetectkotlin

import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.math.round
import kotlin.math.sqrt
import androidx.camera.core.Camera

class ResultHandler(activityView: View, result: FaceLandmarkerHelper.ResultBundle, camera: Camera) {

    // TODO: If these were changed to something else in camera fragment, this WILL break
    private val rounding = Math.pow(10.0, GlobalVars.Companion.cameraValuesRounding)

    init {
        val TVDist = activityView.findViewById<TextView>(R.id.TV_distance)

        var dist = getDistanceFromCamera(result)

        //dist *= 30/0.65
        // 30cm = 0.65
        // 40cm = 0.45
        var out = getRealDistance(dist, rounding)

        TVDist.text = "Distance - ${out}cm"
        //Log.w("a", "Distance - ${out}cm")
        GlobalVars.cameraResults = result
    }

    // function are in companion so they can be used globally
    companion object {

        const val height = 4
        const val width = 3

        // Converts distance given by camera to distance in centimeters
        fun getRealDistance(cameraDistance: Double, rounding: Double, isRounded: Boolean = true): Double{
            // TODO: remove the rounding variable, and access it directly from GlobalVars from here

            val distance = GlobalVars.distance[GlobalVars.cameraAlgorithmIndex]
            val distanceMultiplier = GlobalVars.distanceMultiplier[GlobalVars.cameraAlgorithmIndex]

            var out = distance * distanceMultiplier / cameraDistance
            if(isRounded) {
                out = round(out * rounding) / rounding
            }
            return out
        }

        // Gets raw distance from camera as a fraction in range of 0 to 1
        fun getDistanceFromCamera(result: FaceLandmarkerHelper.ResultBundle): Double{

            var dist: Double = -1.0
            when(GlobalVars.cameraAlgorithmIndex) {
                    0 -> dist = eyeToEyeAlgorithm(result)
                    1 -> dist = moustacheToNoseBridgeAlgoriths(result)
            }

            return dist
        }

        fun eyeToEyeAlgorithm(result: FaceLandmarkerHelper.ResultBundle) : Double{
            val rightEye =  result.result.faceLandmarks()[0][468]
            val leftEye = result.result.faceLandmarks()[0][473]

            val rx = rightEye.x() * width * 1.0
            val ry = rightEye.y() * height * 1.0
            val lx = leftEye.x() * width * 1.0
            val ly = leftEye.y() * height * 1.0

            val dist = sqrt( Math.pow(rx-lx, 2.0) + Math.pow(ry-ly, 2.0) )

            return dist
        }

        fun moustacheToNoseBridgeAlgoriths(result: FaceLandmarkerHelper.ResultBundle) : Double{
            // TODO: TODO: test this algorithm, maybe make another text in layout to show this and the eyetoeye algorithm result
            val moustache = result.result.faceLandmarks()[0][164]
            val noseBridge = result.result.faceLandmarks()[0][168]

            val mx = moustache.x() * width * 1.0
            val my = moustache.y() * height * 1.0
            val nx = noseBridge.x() * width * 1.0
            val ny = noseBridge.y() * width * 1.0

            val dist = sqrt( Math.pow(mx-nx, 2.0) + Math.pow(my-ny, 2.0) )
            Log.w("moustacheToNoseBridge", dist.toString())

            return dist
        }

        // Callibrates camera parameters
        // Distance is set to given value
        // Multiplier is set to the distance on screen as a fraction of the total (from 0 to 1)
        fun calibrate(distance: Double){
            GlobalVars.distanceMultiplier[GlobalVars.cameraAlgorithmIndex] = getDistanceFromCamera(GlobalVars.cameraResults)
            GlobalVars.distance[GlobalVars.cameraAlgorithmIndex] = distance
        }

        // Resets calibration for camera parameters.
        // Works only for current index
        fun resetCalibration(){
            GlobalVars.distanceMultiplier[GlobalVars.cameraAlgorithmIndex] = GlobalVars.defaultMultiplier[GlobalVars.cameraAlgorithmIndex]
            GlobalVars.distance[GlobalVars.cameraAlgorithmIndex] = GlobalVars.defaultDistance[GlobalVars.cameraAlgorithmIndex]
        }
    }
}