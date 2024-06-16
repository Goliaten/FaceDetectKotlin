package com.example.facedetectkotlin

import android.content.ClipData.Item
import android.content.Context
import com.example.facedetectkotlin.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.facedetectkotlin.fragments.BlankFragment
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        readSharedPreferences()
    }

    override fun onPause() {
        super.onPause()
        saveSharedPreferences()
    }

    fun readSharedPreferences(){
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val v1 = JSONArray(
            sharedPref.getString(
                "distance",
                JSONArray(GlobalVars.defaultDistance
                ).toString())!!
        )
        val v2 = JSONArray(
            sharedPref.getString(
                "distanceMultiplier",
                JSONArray(GlobalVars.defaultMultiplier
                ).toString())!!
        )

        GlobalVars.distance = Array(v1.length()){ v1.getDouble(it) }
        GlobalVars.distanceMultiplier = Array(v2.length()){ v2.getDouble(it) }
        //Log.w("readSharedPreferences", a[1].toString())
        //Log.w("readSharedPreferences", v2)
    }

    fun saveSharedPreferences(){
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString("distance", JSONArray(GlobalVars.distance).toString())
            putString("distanceMultiplier", JSONArray(GlobalVars.distanceMultiplier).toString())
            apply()
        }
    }

    fun buttonClick(view: View) {
        Toast.makeText(this, "Boobs", Toast.LENGTH_LONG).show();
    }

    fun buttonClick(item: MenuItem) {
        Toast.makeText(this, "Booba", Toast.LENGTH_LONG).show();
    }

    fun calibrateResults(view: View){
        if (findViewById<EditText>(R.id.ET_distance).text.toString() != ""){
            var userDistance = findViewById<EditText>(R.id.ET_distance).text.toString().toDouble()
            ResultHandler.calibrate( userDistance )
            Toast.makeText(this, "Współczynni kamery zmieniony", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Brak ustawionego dystansu", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToCamera(item: MenuItem) {


        Navigation.findNavController(
            this@MainActivity,
            R.id.fragmentContainerView
        ).navigate(
            R.id.action_to_camera_fragment
        )
        activityMainBinding.drawerLayout.closeDrawers()
    }

    fun goToSettings(item: MenuItem) {
        Navigation.findNavController(
            this@MainActivity,
            R.id.fragmentContainerView
        ).navigate(
            R.id.action_to_settings_fragment
        )
        activityMainBinding.drawerLayout.closeDrawers()
    }

    fun resetResultSettings(item: MenuItem) {
        ResultHandler.resetCalibration()
        Toast.makeText(this, "Współczynni kamery zresetowany", Toast.LENGTH_SHORT).show()
        activityMainBinding.drawerLayout.closeDrawers()
    }


}