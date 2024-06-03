package com.example.facedetectkotlin

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.fragment.app.FragmentContainerView
import kotlin.math.round
import kotlin.math.sqrt
import androidx.camera.core.Camera

class ResultHandler(activityView: View, result: FaceLandmarkerHelper.ResultBundle, camera: Camera) {

    // TODO: If these were changed to something else in camera fragment, this WILL break
    private val rounding = Math.pow(10.0, GlobalVars.Companion.cameraValuesRounding)

    init {
        var TVDist = activityView.findViewById<TextView>(R.id.TV_distance)

        var dist = getDistanceFromCamera(result)

        //dist *= 30/0.65
        // 30cm = 0.65
        // 40cm = 0.45
        var out = getRealDistance(dist, rounding)

        TVDist.text = "Distance - ${out}cm"
        Log.w("a", "Distance - ${out}cm")
        GlobalVars.cameraResults = result
    }

    companion object {

        const val height = 4
        const val width = 3

        // gets distance converted to cm
        fun getRealDistance(dist: Double, rounding: Double, isRounded: Boolean = true): Double{
            var out = GlobalVars.distance * GlobalVars.distanceMultiplier  / dist
            if(isRounded) {
                out = round(out * rounding) / rounding
            }
            return out
        }

        // Gets raw distance
        fun getDistanceFromCamera(result: FaceLandmarkerHelper.ResultBundle): Double{
            val right_eye =  result.result.faceLandmarks()[0][468]
            val left_eye = result.result.faceLandmarks()[0][473]

            val rounding = Math.pow(10.0, 3.0)

            val rx = right_eye.x()* width * 1.0
            val ry = right_eye.y()* height * 1.0
            val lx = left_eye.x()* width * 1.0
            val ly = left_eye.y()* height * 1.0

            val dist = sqrt( Math.pow(rx-lx, 2.0) + Math.pow(ry-ly, 2.0) )

            return dist
        }

        fun calibrate(spinner: Spinner): Double {
            // TODO: remove context - used for debugging purpose

            // get set distance to screen from spinner
            val dist_selected = GlobalVars.spinnerDistanceItems[ spinner.selectedItemPosition ].toInt()

            // get distance acquired with model
            val cam_dist = getDistanceFromCamera(GlobalVars.cameraResults)

            // calculate the new distance multi

            val new_multi = cam_dist

            GlobalVars.distanceMultiplier = new_multi
            GlobalVars.distance = dist_selected

            return new_multi
        }

        fun resetCalibration(){
            GlobalVars.distanceMultiplier = GlobalVars.defaultMultiplier
            GlobalVars.distance = GlobalVars.defaultDistance
        }
    }
}