package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.abdullah.ams.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.checkerframework.common.subtyping.qual.Bottom

class MainActivity : AppCompatActivity(){
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var sss: String
    lateinit var databaseReference:DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            databaseReference = FirebaseDatabase.getInstance().getReference("AllData/User")
            val tvmain = findViewById<TextView>(R.id.mainName)

            databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children){
                        if (i.key == uid) {
                            val ss = i.child("level").getValue().toString()
                           val tvma = i.child("firstName").getValue().toString()
                            sss = ss
                            tvmain.setText(tvma)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        } else {
            // User is not logged in
        }

        val btnjou = findViewById<Button>(R.id.MainEnter)
        btnjou.setOnClickListener{
            if(sss == "Admain") {
                Intent(this, Admin::class.java).also { startActivity(it) }
            }
            else if(sss == "Stuff"){
                    Intent(this,teacher::class.java).also { startActivity(it) }
            }else{
                Intent(this,UserHomePage::class.java).also { startActivity(it) }
            }
        }
    }
}