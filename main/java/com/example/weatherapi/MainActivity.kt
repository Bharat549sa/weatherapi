package com.example.weatherapi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.location.Location
import com.example.weatherapi.R
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.provider.SyncStateContract
import android.provider.UserDictionary.Words.APP_ID
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weatherapi.databinding.ActivityMainBinding

import com.example.weatherapi.network.WeatherService
import com.example.weatherapi.utils.Constants
import com.example.weatherapi.utils.Constants.BASE_URL
import com.example.weatherapi.utils.Constants.isNetworkAvailable
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import retrofit.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    // A fused location client variable which is further user to get the user's current location
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    // A global variable for Progress Dialog
    private var mProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        // Initialize the Fused location variable
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

       // if () {//!isLocationEnabled()
            Toast.makeText(
                this,
                "Your location provider is turned off. Please turn it on.",
                Toast.LENGTH_SHORT
            ).show()

            // This will redirect you to settings from where you need to turn on the location provider.
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
//        } else {
//            Dexter.withActivity(this)
//                .withPermissions(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//                .withListener(object : MultiplePermissionsListener {
//                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                        if (report!!.areAllPermissionsGranted()) {
//                            requestLocationData()
//                        }
//
//                        if (report.isAnyPermissionPermanentlyDenied) {
//                            Toast.makeText(
//                                this@MainActivity,
//                                "You have denied location permission. Please allow it is mandatory.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }

                //    override fun onPermissionRationaleShouldBeShown(
                  //      permissions: MutableList<PermissionRequest>?,
                  //      token: PermissionToken?
                   // ) {
                      //  showRationalDialogForPermissions()
                  //  }
               // }//)//nSameThread()
               // .check()
       // }
    }
    /**
     * Function is used to get the temperature unit value.
     */
    private fun getUnit(value: String): String? {
        Log.i("unitttttt", value)
        var value = "°C"
        if ("US" == value || "LR" == value || "MM" == value) {
            value = "°F"
        }
        return value
    }

    /**
     * The function is used to get the formatted time based on the Format and the LOCALE we pass to it.
     */
    private fun unixTime(timex: Long): String? {
        val date = Date(timex * 1000L)
        @SuppressLint("SimpleDateFormat") val sdf =
            SimpleDateFormat("HH:mm:ss")
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
    private fun getLocationWeatherDetails(latitude:Double, longitude:Double)
    {
        //if(SyncStateContract.Constants.isNetworkAvailable(this))
            if (Constants.isNetworkAvailable(this@MainActivity)) {
        //{
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service: WeatherService = retrofit
                .create<WeatherService>(WeatherService::class.java)

                /** An invocation of a Retrofit method that sends a request to a web-server and returns a response.
                 * Here we pass the required param in the service
                 */
              //  val listCall: Call<WeatherResponse> = service.getWeather(
                //    latitude, longitude, Constants.METRIC_UNIT, Constants.APP_ID
           // )

            showCustomProgressDialog() // Used to show the progress dialog

            // Callback methods are executed using the Retrofit callback executor.
//            listCall.enqueue(object : Callback<WeatherResponse> {
//                @SuppressLint("SetTextI18n")
//                override fun onResponse(
//                    response: Response<WeatherResponse>,
//                    retrofit: Retrofit
//                ) {
//
//                    // Check weather the response is success or not.
//                    if (response.isSuccess) {
//
//                        hideProgressDialog() // Hides the progress dialog
//
//                        /** The de-serialized response body of a successful response. */
//                        val weatherList: WeatherResponse = response.body()
//                        Log.i("Response Result", "$weatherList")
//
//                        // TODO (STEP 6: Call the setup UI method here and pass the response object as a parameter to it to get the individual values.)
//                        // START
//                        setupUI(weatherList)
//                        // END
//                    } else {
//                        // If the response is not success then we check the response code.
//                        val sc = response.code()
//                        when (sc) {
//                            400 -> {
//                                Log.e("Error 400", "Bad Request")
//                            }
//                            404 -> {
//                                Log.e("Error 404", "Not Found")
//                            }
//                            else -> {
//                                Log.e("Error", "Generic Error")
//                            }
//                        }
//                    }
//                }
//                override fun onFailure(t: Throwable) {
//                    hideProgressDialog() // Hides the progress dialog
//                    Log.e("Errorrrrr", t.message.toString())
//                }
//            })
//        }else{
//            Toast.makeText(this@MainActivity, "NO internet connection available",
//            Toast.LENGTH_SHORT)
//        }
    }

    /**
     * A function to request the current location. Using the fused location provider client.
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationData() {

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    // TODO (STEP 5: We have set the values to the UI and also added some required methods for Unit and Time below.)
    /**
     * Function is used to set the result in the UI elements.
     */
    private fun setupUI(weatherList: WeatherResponse) {

        // For loop to get the required data. And all are populated in the UI.
        for (z in weatherList.weather.indices) {
            Log.i("NAMEEEEEEEE", weatherList.weather[z].main)

            tv_main.text = weatherList.weather[z].main
            tv_main_description.text = weatherList.weather[z].description
            tv_temp.text =
                weatherList.main.temp.toString() + getUnit(application.resources.configuration.locales.toString())
            tv_humidity.text = weatherList.main.humidity.toString() + " per cent"
            tv_min.text = weatherList.main.tempMin.toString() + " min"
            tv_max.text = weatherList.main.tempMax.toString() + " max"
            tv_speed.text = weatherList.wind.speed.toString()
            tv_name.text = weatherList.name
            tv_country.text = weatherList.sys.country
            tv_sunrise_time.text = unixTime(weatherList.sys.sunrise.toLong())
            tv_sunset_time.text = unixTime(weatherList.sys.sunset.toLong())

            // Here we update the main icon
            when (weatherList.weather[z].icon) {
                "01d" -> iv_main.setImageResource(R.drawable.sunny)
                "02d" -> iv_main.setImageResource(R.drawable.cloud)
                "03d" -> iv_main.setImageResource(R.drawable.cloud)
                "04d" -> iv_main.setImageResource(R.drawable.cloud)
                "04n" -> iv_main.setImageResource(R.drawable.cloud)
                "10d" -> iv_main.setImageResource(R.drawable.rain)
                "11d" -> iv_main.setImageResource(R.drawable.storm)
                "13d" -> iv_main.setImageResource(R.drawable.snowflake)
                "01n" -> iv_main.setImageResource(R.drawable.cloud)
                "02n" -> iv_main.setImageResource(R.drawable.cloud)
                "03n" -> iv_main.setImageResource(R.drawable.cloud)
                "10n" -> iv_main.setImageResource(R.drawable.cloud)
                "11n" -> iv_main.setImageResource(R.drawable.rain)
                "13n" -> iv_main.setImageResource(R.drawable.snowflake)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val latitude = mLastLocation.latitude
            Log.i("Current Latitude", "$latitude")

            val longitude = mLastLocation.longitude
            Log.i("Current Longitude", "$longitude")

            getLocationWeatherDetails(latitude, longitude)
        }
    }
    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun showCustomProgressDialog() {
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        mProgressDialog!!.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    private fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }
}