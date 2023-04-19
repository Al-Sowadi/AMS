package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference



class Profile : AppCompatActivity() {
    lateinit var firebaseUser: FirebaseUser
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var uid :String
    lateinit var ss:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
//        databaseReference =FirebaseDatabase.getInstance().getReference("AllData/User")

        uid = firebaseUser.uid
        val database11 = FirebaseDatabase.getInstance()
        val myRef11 = database11.getReference("AllData/User")
        val BackWordpr = findViewById<Button>(R.id.BackWordpr)

        val extras = intent.extras
        val userName: String?

        if (extras != null) {
            userName = extras.getString("id")
            // and get whatever type user account id is
            BackWordpr.setOnClickListener{
                if(userName == "teacher"){
                    Intent(this,teacher::class.java).also { startActivity(it) }
                }
                if(userName == "admin"){
                    Intent(this,Admin::class.java).also { startActivity(it) }
                }
            }

        }

        val recyclerview1 = findViewById<RecyclerView>(R.id.recyclerview3)
        recyclerview1.layoutManager = LinearLayoutManager(this)
        val datauser = ArrayList<userDataAdpter>()
        var getdata1 = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    if (i.key == uid) {
                        var m11 = i.child("firstName").getValue()
                        var m22 = i.child("lastName").getValue()
                        var m33 = i.child("age").getValue()
                        var m4 = i.child("gender").getValue()
                        var m5 = i.child("email").getValue()
                        var m6 = i.child("level").getValue()
                        datauser.add(
                            userDataAdpter(
                                m11.toString(),
                                m22.toString(),
                                m33.toString(),
                                m4.toString(),
                                m5.toString(),
                                m6.toString()
                            )
                        )
                        break
                    }
                }
                val adapter1 = userAdapter(datauser)

                // Setting the Adapter with the recyclerview
                recyclerview1.adapter = adapter1
            }
        }
//        myRef11.addValueEventListener(getdata1)
        myRef11.addListenerForSingleValueEvent(getdata1)

    }

}
