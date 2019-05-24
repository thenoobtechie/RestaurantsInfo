package com.example.restaurantsinfo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.restaurantsinfo.Utility.RESTAURANT
import com.example.restaurantsinfo.Utility.RESTAURANT_ID
import com.example.restaurantsinfo.adapter.RecyclerViewItemClickListener
import com.example.restaurantsinfo.adapter.RestaurantsListAdapter
import com.example.restaurantsinfo.model.Restaurant
import com.example.restaurantsinfo.viewmodel.MainActivityViewModel
import com.example.restaurantsinfo.viewmodel.factory.MainViewModelFactory
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.ConnectionResult.SUCCESS
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewItemClickListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    var viewModel : MainActivityViewModel? = null
    var listAdapter : RestaurantsListAdapter? = null

    var googleApiClient: GoogleApiClient? = null
    var locatioRequest: LocationRequest? = null
    var location: Location? = null
    var locationCallback : LocationCallback? = null
    var fusedLocationProviderClient : FusedLocationProviderClient? = null

    var permissions: ArrayList<String> = ArrayList()
    var permissionsToRequestList: ArrayList<String> = ArrayList()

    val LOCATION_REQ_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imgContainerView = restaurantsList
        val androidRobotView = restaurantsList

        // inside your activity (if you did not enable transitions in your theme)
        /*with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            // set an exit transition
            exitTransition = Explode()
        }*/

        setContentView(R.layout.activity_main)

        initLocationServices()

        val factory = MainViewModelFactory(application, Handler.Callback {

            listAdapter?.restaurantsList = viewModel!!.restaurants
            listAdapter?.notifyDataSetChanged()
            true
        })

        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        listAdapter = RestaurantsListAdapter(viewModel!!.restaurants, this)
        restaurantsList.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        restaurantsList.adapter = listAdapter
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.setOnClickListener { searchView.isIconified = false }

        viewModel?.fetchRestaurants("")

        fetchNewLocation.setOnClickListener(this)

    }


    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.fetchNewLocation -> requestNewUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewUpdates() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionsToRequestList.size > 0){
            requestPermissions(permissionsToRequestList.toTypedArray(), LOCATION_REQ_CODE)
            return
        }

        fusedLocationProviderClient?.requestLocationUpdates(locatioRequest, locationCallback, null)
    }

    override fun onListItemClick(v: View, restaurant: Restaurant?, vararg pair: Pair<View, String>?) {

        val optionsCompat : ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pair)

        val intent = Intent(applicationContext, RestaurantDetailActivity::class.java)
        intent.putExtra(RESTAURANT_ID, restaurant!!.id)
        intent.putExtra(RESTAURANT, restaurant)
//        startActivity(intent)
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun onStart() {
        super.onStart()

        /*connectGoogleApiClient()*/
    }

    fun connectGoogleApiClient(){
        if(!googleApiClient?.isConnected!!)
            googleApiClient?.connect()
    }

    override fun onResume() {
        super.onResume()

        checkPlayServices()
    }

    override fun onPause() {
        super.onPause()

        removeLocationUpdates()
    }

    private fun removeLocationUpdates() {

        if (googleApiClient!=null && googleApiClient?.isConnected!!) {
            fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
            googleApiClient?.disconnect()
        }
    }

    private fun checkPlayServices(): Boolean {

        val apiAvailability : GoogleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(applicationContext)

        if (resultCode != SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, LOCATION_REQ_CODE).show()
            }

            return false
        }
        return true
    }

    @SuppressLint("MissingPermission")
    private fun initLocationServices() {


        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

        permissionsToRequestList = permissionsToRequest(permissions)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionsToRequestList.size > 0){
            requestPermissions(permissionsToRequestList.toTypedArray(), LOCATION_REQ_CODE)
            return
        }

        googleApiClient = GoogleApiClient.Builder(applicationContext)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        locatioRequest = LocationRequest.create().setNumUpdates(1).setInterval(0)
        locatioRequest?.priority = PRIORITY_HIGH_ACCURACY
        locatioRequest?.smallestDisplacement = 0f

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){

                    updateUIWithLocation(location)
                }
            }

            override fun onLocationAvailability(p0: LocationAvailability?) {
                super.onLocationAvailability(p0)
            }
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        connectGoogleApiClient()
//        updateUIWithLocation(fusedLocationProviderClient?.lastLocation)


    }

    private fun isPermissionsGrantedByUser(): Boolean {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsToRequestList.toTypedArray(), LOCATION_REQ_CODE)
            }
            return false
        }
        return true
    }

    private fun updateUIWithLocation(location: Location?) {

        viewModel?.location = location
        viewModel?.fetchRestaurants("")
        populateAddress(location)
        removeLocationUpdates()
    }

    private var queryTextListener = object : SearchView.OnQueryTextListener {

        val handler = Handler()

        override fun onQueryTextSubmit(queryStr: String?): Boolean {

            searchRestaurant(queryStr)
            return true
        }

        override fun onQueryTextChange(queryStr: String?): Boolean {

            searchRestaurant(queryStr)
            return true
        }

        private fun searchRestaurant(queryStr: String?) {

            Log.d("****Test Logs : ", "Handler run")
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({ viewModel?.fetchRestaurants(queryStr!!) }, 300)
        }
    }

    fun permissionsToRequest(permissions: ArrayList<String>): ArrayList<String> {

        val permissionsToRequestList = ArrayList<String>()
        for (permission in permissions) {
            if (!hasPermissions(permission))
                permissionsToRequestList.add(permission)
        }

        return permissionsToRequestList
    }

    fun hasPermissions(permission: String): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return checkSelfPermission(permission) == PERMISSION_GRANTED

        return false
    }

    private fun populateAddress(location: Location?) {

        location?.let {
            val addresses : ArrayList<Address> = Geocoder(applicationContext).getFromLocation(location.latitude, location.longitude, 5) as ArrayList<Address>
            val address = addresses[0]

            val appendedStr = getString(R.string.locationTxt, address.locality, address.adminArea, address.countryName)
            currentLocationText.text = appendedStr

        } ?: Toast.makeText(applicationContext, "Location is null", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("****LocationTest", "ConnectionFailed" + p0.errorMessage)
    }

    override fun onConnected(p0: Bundle?) {

        requestNewUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("****LocationTest", "ConnectionFailed" + p0.toString())
    }
}
