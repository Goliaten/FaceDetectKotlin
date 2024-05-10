package com.example.facedetectkotlin

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.fragment.app.FragmentContainerView
import kotlin.math.round
import kotlin.math.sqrt
import androidx.camera.core.Camera

class ResultHandler(activityView: View, result: FaceLandmarkerHelper.ResultBundle, camera: Camera) {

    init {
        var height = activityView.context.display!!.height //2158
        var width = activityView.context.display!!.width //1080

        height=4
        width=3
        //camera.cameraInfo.intrinsicZoomRatio
        /*
        camera.
        val cameraId = Camera2CameraInfo.extractCameraCharacteristics(camera.cameraInfo)
        val cameraManager = activityView.context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        val streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val outputSizes = streamConfigurationMap.getOutputSizes(format)
        */

        //var TVLeft = activityView.findViewById<TextView>(R.id.TV_left_eye)
        //var TVRight = activityView.findViewById<TextView>(R.id.TV_right_eye)
        var TVDist = activityView.findViewById<TextView>(R.id.TV_distance)

        val right_eye =  result.result.faceLandmarks()[0][468]
        val left_eye = result.result.faceLandmarks()[0][473]

        val rounding = Math.pow(10.0, 3.0)

        var rx = right_eye.x()* width * 1.0
        var ry = right_eye.y()* height * 1.0
        var lx = left_eye.x()* width * 1.0
        var ly = left_eye.y()* height * 1.0

        var dist = sqrt( Math.pow(rx-lx, 2.0) + Math.pow(ry-ly, 2.0) )


        //dist *= 30/0.65
        // 30cm = 0.65
        // 40cm = 0.45
        var out = 30 * 0.65 / dist

        //TODO bit faulty, all these values show relative position on the screen, so vertically it shows one distance, horizontally it shows another
        // maybe get screen resolution and scale these values?
        // then maybe get camera parameters? cause camera lens may also change how the image is shown, which will skew the distance shown on different devices

        // gonna assume screen resolution ~= camera resolution
        rx = round(rx*rounding)/rounding
        ry = round(ry*rounding)/rounding
        lx = round(lx*rounding)/rounding
        ly = round(ly*rounding)/rounding
        dist = round(dist*rounding) / rounding
        out = round(out*rounding) / rounding

        //TVLeft.text = "Right eye - X:${lx} Y:${ly}"
        //TVRight.text = "Left eye - X:${rx} Y:${ry}"
        TVDist.text = "Distance - ${out}cm"
        Log.w("a", "Distance - ${out}cm")
    }

}