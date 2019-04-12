package com.example.ueyz2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_every_store.*
import kotlinx.android.synthetic.main.content_main.*

class EveryStoreActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoreAdapter

    private lateinit var storeList: ArrayList<StoreModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_every_store)
        db=FirebaseFirestore.getInstance()
        val itemsToShow= intent.getIntExtra("toShow",0)

        storeList = ArrayList<StoreModel>()
        recyclerView = storeRecycleView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter = StoreAdapter(this, storeList)
        recyclerView.adapter=adapter
        adapter.setOnItemClickListener(StoreAdapter.OnItemClickListener {
            val intento2 = Intent(this, MapsActivity::class.java)
            intento2.putExtra("lat", storeList[it].latitude)
            intento2.putExtra("long", storeList[it].longitude)
            startActivity(intento2)
        })
        if (itemsToShow==1){
            db.collection("tiendas")
                .whereEqualTo("type", "Abarrotería")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val store = StoreModel(
                            document["description"].toString(),
                            document["latitude"].toString().toFloat(),
                            document["longitude"].toString().toFloat(),
                            document["name"].toString(),
                            document["type"].toString(),
                            R.drawable.tienda
                        )
                        storeList.add(store)
                        adapter.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "Khe hiciste Wuey", Toast.LENGTH_SHORT).show()

                }
        }
        else if (itemsToShow==2) {
            db.collection("tiendas")
                .whereEqualTo("type", "Puesto de comida")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val store = StoreModel(
                            document["description"].toString(),
                            document["latitude"].toString().toFloat(),
                            document["longitude"].toString().toFloat(),
                            document["name"].toString(),
                            document["type"].toString(),
                            R.drawable.food
                        )
                        storeList.add(store)
                        adapter.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "Khe hiciste Wuey", Toast.LENGTH_SHORT).show()

                }
        }
        else if (itemsToShow==3) {
            db.collection("tiendas")
                .whereEqualTo("type", "Otros")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val store = StoreModel(
                            document["description"].toString(),
                            document["latitude"].toString().toFloat(),
                            document["longitude"].toString().toFloat(),
                            document["name"].toString(),
                            document["type"].toString(),
                            R.drawable.other
                        )
                        storeList.add(store)
                        adapter.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "Khe hiciste Wuey", Toast.LENGTH_SHORT).show()

                }
        }
    }
}
