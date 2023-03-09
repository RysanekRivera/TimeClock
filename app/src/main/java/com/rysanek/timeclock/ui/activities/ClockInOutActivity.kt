package com.rysanek.timeclock.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rysanek.timeclock.databinding.ActivityClockInOutBinding
import com.rysanek.timeclock.ui.activities.base.TimeClockActivity
import java.util.concurrent.Executors

class ClockInOutActivity : TimeClockActivity() {

    private lateinit var layout: ActivityClockInOutBinding

    companion object {
        private const val LOCATION_REQUEST_CODE = 1000
        private const val CAMERA_PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout = ActivityClockInOutBinding.inflate(layoutInflater)

        checkCameraPermission()

        layout.btGpsCoordinates.setOnClickListener {
            checkForGpsPermissionsAndRecordLocation()
        }

        setContentView(layout.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, start using location data
                    Log.d("perm", "Location Permission is Granted")
                    recordGpsLocation { lat, long ->
                        Log.d("GPS Coor", "Lat: $lat, Long: $long")
                    }
                } else {
                    Log.d("Perm", "Location Permission is NOT Granted")

                    // Permission is not granted, show a message to the user and do not use location data
                    Toast.makeText(
                        this,
                        "Location permission is required to use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Perm", "Camera Permission is Granted")

                    startCamera()
                } else {
                    Log.d("Perm", "Camera Permission is Not Granted")

                    Toast.makeText(
                        this,
                        "Location permission is required to use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun recordGpsLocation(onCoordinatesReceived: (lat: Double, long: Double) -> Unit) {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {

            try {
                val locationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        // Handle location updates here

                        onCoordinatesReceived(location.latitude, location.longitude)
                    }

                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                locationManager.requestSingleUpdate(
                    LocationManager.GPS_PROVIDER,
                    locationListener,
                    null
                )
            } catch (e: Exception) {
                val locationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        onCoordinatesReceived(location.latitude, location.longitude)
                    }

                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                locationManager.requestSingleUpdate(
                    LocationManager.NETWORK_PROVIDER,
                    locationListener,
                    null
                )
            }

        } else {

            try {
                locationManager.getCurrentLocation(
                    LocationManager.GPS_PROVIDER,
                    null,
                    Executors.newSingleThreadExecutor()
                ) { location ->
                    onCoordinatesReceived(location.latitude, location.longitude)
                }
            } catch (e: Exception) {

                locationManager.getCurrentLocation(
                    LocationManager.NETWORK_PROVIDER,
                    null,
                    Executors.newSingleThreadExecutor()
                ) { location ->
                    onCoordinatesReceived(location.latitude, location.longitude)

                }
            }


        }
    }

    @SuppressLint("MissingPermission")
    private fun getGpsLatLng() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    // Handle location updates here
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("ClockInOutFragment", "Latitude: $latitude, Longitude: $longitude")
                }

                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            locationManager.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                locationListener,
                null
            )

        } else {

            locationManager.getCurrentLocation(
                LocationManager.GPS_PROVIDER,
                null,
                Executors.newSingleThreadExecutor()
            ) { location ->
                val latitude = location?.latitude
                val longitude = location?.longitude
                Log.d("ClockInOutFragment", "Latitude: $latitude, Longitude: $longitude")
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun getCellTowerLatLng() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    // Handle location updates here
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("ClockInOutFragment", "Latitude: $latitude, Longitude: $longitude")
                }

                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            locationManager.requestSingleUpdate(
                LocationManager.NETWORK_PROVIDER,
                locationListener,
                null
            )

        } else {

            locationManager.getCurrentLocation(
                LocationManager.NETWORK_PROVIDER,
                null,
                Executors.newSingleThreadExecutor()
            ) { location ->
                val latitude = location?.latitude
                val longitude = location?.longitude
                Log.d("ClockInOutFragment", "Latitude: $latitude, Longitude: $longitude")
            }

        }
    }

    private fun checkForGpsPermissionsAndRecordLocation() {

        // Request a single location update
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            recordGpsLocation{ lat, long ->
                Log.d("GPS Coor", "Lat: $lat, Long: $long")
            }
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(layout.cameraPreview.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider?.unbindAll()

                // Bind use cases to camera
                cameraProvider?.bindToLifecycle(this, cameraSelector, preview)

            } catch (exc: Exception) {
                Log.e("ClockOutFrag", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

}