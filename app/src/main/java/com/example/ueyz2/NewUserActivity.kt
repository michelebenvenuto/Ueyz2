package com.example.ueyz2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_user.*
import java.util.*

class NewUserActivity : AppCompatActivity() {
    var db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

    }
    fun createNewUser(view: View){
        var noteDataMap= HashMap<String,Any>()
        if(userNameInput.text.toString() != "" && eMailInput.text.toString()!="" && password.text.toString()!= "" && checkPassword.text.toString()!=""){
            if (password.text.toString().equals(checkPassword.text.toString())){
                noteDataMap.put("eMail",eMailInput.text.toString())
                noteDataMap.put("name",userNameInput.text.toString())
                noteDataMap.put("pasword",password.text.toString())
                noteDataMap.put("ratingsMade","0")
                db.collection("usuarios")
                    .add(noteDataMap)
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                        Toast.makeText(applicationContext,"Se creo el usuario con exito", Toast.LENGTH_SHORT).show()
                        finish()

                    })
                    .addOnFailureListener(OnFailureListener {
                            e -> Toast.makeText(applicationContext,"Error al crear el usuario", Toast.LENGTH_SHORT).show()
                    })
            }else{Toast.makeText(applicationContext,"No se ha verificado la contraseña", Toast.LENGTH_SHORT).show()}
        }else{Toast.makeText(applicationContext,"Por favor llenar todos los campos", Toast.LENGTH_SHORT).show()}
    }
}
