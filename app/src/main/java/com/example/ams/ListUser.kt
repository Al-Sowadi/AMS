package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ListUser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        val BackWordTeacher = findViewById<Button>(R.id.BackWordTeacher)
        val extras = intent.extras
        val userName: String?

        if (extras != null) {
            userName = extras.getString("id")
            // and get whatever type user account id is
            BackWordTeacher.setOnClickListener{
                if(userName == "teacher"){
                    Intent(this,teacher::class.java).also { startActivity(it) }
                }
                if(userName == "admin"){
                    Intent(this,Admin::class.java).also { startActivity(it) }
                }
            }

        }
        val database1 = FirebaseDatabase.getInstance()
        val myRef1 = database1.getReference("AllData/User")


        val recyclerview1 = findViewById<RecyclerView>(R.id.recyclerview1)
        recyclerview1.layoutManager = LinearLayoutManager(this)
        val datauser = ArrayList<userDataAdpter>()
        var getdata1 = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var m11 = i.child("firstName").getValue()
                    var m22 = i.child("lastName").getValue()
                    var m33 = i.child("age").getValue()
                    var m4 = i.child("gender").getValue()
                    var m5 = i.child("email").getValue()
                    var m6 = i.child("level").getValue()
                    datauser.add(userDataAdpter(m11.toString(),m22.toString(),m33.toString(),m4.toString(),m5.toString(),m6.toString()))

                }
                val adapter1 = userAdapter(datauser)

                // Setting the Adapter with the recyclerview
                recyclerview1.adapter = adapter1
            }
        }
//        myRef1.addValueEventListener(getdata1)
        myRef1.addListenerForSingleValueEvent(getdata1)
    }
}