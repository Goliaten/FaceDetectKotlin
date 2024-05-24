package com.example.facedetectkotlin

import android.content.ClipData.Item
import com.example.facedetectkotlin.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityMainBinding.root)
        setupMenuButtons()
    }

    private fun setupMenuButtons() {

    }

    fun buttonClick(view: View) {
        Toast.makeText(this, "Boobs", Toast.LENGTH_LONG).show();
    }

    fun buttonClick(item: MenuItem) {
        Toast.makeText(this, "Booba", Toast.LENGTH_LONG).show();
    }

    fun calibrateResults(view: View){
        val multi = ResultHandler.calibrate(findViewById<Spinner>(R.id.cameraSpinner))
        Toast.makeText(this, "Współczynni kamery zmieniony", Toast.LENGTH_SHORT).show()
    }

    fun goToCamera(item: MenuItem) {
        Navigation.findNavController(
            this@MainActivity,
            R.id.fragmentContainerView
        ).navigate(
            R.id.action_blankFragment_to_cameraFragment2
        )
    }

    fun goToSettings(item: MenuItem) {

        // TODO: This right now works only for going from camera fragment to blank fragment
        //       find a way to do this universally, or enjoy the pain of doing it for every fragment
        Navigation.findNavController(
            this@MainActivity,
            R.id.fragmentContainerView
        ).navigate(
            R.id.action_cameraFragment2_to_blankFragment
        )
    }


}