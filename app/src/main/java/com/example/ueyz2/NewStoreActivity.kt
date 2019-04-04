package com.example.ueyz2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_new_store.*
import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class NewStoreActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_store)

        getLatitude(View(applicationContext))
        getLongitude(View(applicationContext))

        val dropdown = findViewById<Spinner>(R.id.spinner)
        val items = arrayOf("Puesto de comida", "Librería", "Abarrotería", "Otros")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        var typeSelected = ""
        var isSelected = false
        dropdown.adapter = adapter

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                isSelected = true
                typeSelected = items[position]
                Toast.makeText(this@NewStoreActivity, spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()

            }
        }
    }

    fun showMap(v: View){
        val mapIntent = Intent(this, MapsActivity::class.java)
        startActivity(mapIntent)
    }

    fun createNewStore(v : View){
        if (new_store_name == null) {
            Toast.makeText(this@NewStoreActivity, "No se puede ingresar una tienda sin nombre", Toast.LENGTH_LONG).show()
        }else{
            val newActivity = Intent(this, EveryStoreActivity::class.java)
            var currentLatitude = getLatitude(View(applicationContext))
            var currentLongitude = getLatitude(View(applicationContext))

            var storeDataMap = HashMap<String, Any>()

            storeDataMap.put("name", new_store_name.text.toString())
            storeDataMap.put("type", spinner.selectedItem.toString())
            storeDataMap.put("description", new_store_description.text.toString())
            storeDataMap.put("longitude", latitude)
            storeDataMap.put("latitude", longitude)

            db.collection("tiendas")
                .add(storeDataMap).
                    addOnSuccessListener ({_ ->
                        Toast.makeText(applicationContext, "Se creó con éxito la nueva tienda", Toast.LENGTH_SHORT).show()
                        finish()
                    })
                .addOnFailureListener {
                    Toast.makeText(this@NewStoreActivity, "No se pudo crear la nueva tienda", Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun getLatitude(v:View){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var locationToReturn = 0.0


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1)

        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Toast.makeText(this, "Latitud de ${location!!.latitude} y longitud de ${location.longitude}", Toast.LENGTH_SHORT).show()
                    latitude = location.latitude
                }
        }
    }

    fun getLongitude(v:View){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var locationToReturn = 0.0

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1)

        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Toast.makeText(this, "Latitud de ${location!!.latitude} y longitud de ${location!!.longitude}", Toast.LENGTH_SHORT).show()
                    longitude = location.longitude
                }
        }
    }
}