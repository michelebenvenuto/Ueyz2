package com.example.ueyz2

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var db: FirebaseFirestore
    private lateinit var userName:String
    private lateinit var userEmail:String
    private lateinit var ratingsMade:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        db=FirebaseFirestore.getInstance()
        val userId = intent.getStringExtra("userId")
        val userRef=db.collection("usuarios").document(userId)

        //Codigo que se utiliza para obtener la informacion del usuario que hizo logging
        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    userName=document["name"].toString()
                    userEmail=document["eMail"].toString()
                    ratingsMade=document["ratingsMade"].toString()
                    userSpace.text=userName
                    emailSpace.text=userEmail
                } else {
                    Toast.makeText(applicationContext,"Error1",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext,"Error2",Toast.LENGTH_SHORT).show()
            }


        button5.setOnClickListener {
            val intento2 = Intent(this, EveryStoreActivity::class.java)
            intento2.putExtra("toShow",1)
            startActivity(intento2)
        }
        button6.setOnClickListener {
            val intento2 = Intent(this, EveryStoreActivity::class.java)
            intento2.putExtra("toShow",2)
            startActivity(intento2)
        }
        button7.setOnClickListener {
            val intento2 = Intent(this, EveryStoreActivity::class.java)
            intento2.putExtra("toShow",3)
            startActivity(intento2)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_map -> {
                // Handle the camera action
            }
            R.id.nav_place -> {

            }
            R.id.nav_profile -> {

            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
