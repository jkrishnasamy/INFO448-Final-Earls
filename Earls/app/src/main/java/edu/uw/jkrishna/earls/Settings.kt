package edu.uw.jkrishna.earls

import android.Manifest
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*
import android.widget.Toast
import android.location.Geocoder
import android.location.LocationManager
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.*


var userName = "Not Set"
var phoneNumer = "Not Set"
var userAddress = "Not Set"
class Settings : AppCompatActivity() {

    var lat: Double? = null
    var long: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        getLocation()

        if(phoneNumer != "Not Set" || userAddress != "Not Set" || userName != "Not Set"){
            name.setText(userName)
            phoneNumber.setText(phoneNumer)
            address.setText(userAddress)
        }


            updateInfo.setOnClickListener {
                userName = name.text.toString()
                phoneNumer = phoneNumber.text.toString()
                userAddress = address.text.toString()
                Toast.makeText(this, "Delivery Details Updated", Toast.LENGTH_LONG).show()

            }

        locationButton.setOnClickListener {

            val geocoder = Geocoder(this, Locale.getDefault())
            if(lat != null || long != null) {
                val addresses = geocoder.getFromLocation(lat!!, long!!, 1)
                val firstAdd = addresses.get(0)
                address.setText(firstAdd.getAddressLine(0) + ", " + firstAdd.locality + ", " + firstAdd.adminArea + " " + firstAdd.postalCode)
            }
            else{
                Toast.makeText(this, "Still Getting Location. Try Again", Toast.LENGTH_SHORT).show()
            }

        }
    }


    fun getLocation() {

        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        var locationListener = object : LocationListener{
            override fun onLocationChanged(location: Location?) {
                lat = location!!.latitude
                long = location!!.longitude
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

        }

        try {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex:SecurityException) {
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
            return
        }
        locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> Toast.makeText(applicationContext, "Need Permissions", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }


}
