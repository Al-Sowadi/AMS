package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.abdulllah.ams.All
import com.example.ams.Individual
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class Testing : AppCompatActivity() {
    lateinit var sss: String
    lateinit var databaseReference: DatabaseReference
    lateinit var tid:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        hhhhhhh = findViewById(R.id.hhhhhhh)
//        setContentView(R.layout.activity_testing)
//        val currentUser = Firebase.auth.currentUser
//        if (currentUser != null) {
//            val uid = currentUser.uid
//            tid = findViewById<Button>(R.id.tid)
//            tid.setOnClickListener{
//
//
//
//                    infor()
//
//                    Toast.makeText(this,"jjjjjjjjjj",Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }

//
//    private fun infor() {
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("AllData/User")
//        databaseReference.addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (i in snapshot.children){
//                    val ss = i.child("email").getValue().toString()
//                    if(ss == "st@lpu.in"){
//                        tid.setText(i.key)
//                        val database = Firebase.database.reference
//                        val userId = UUID.randomUUID().toString()
//                        val currentTime = LocalTime.now()
//                        val alluser = Individual("TitleSendNote.text.toString()","messageSendNote.text.toString()","currentTime.toString()",tid.text.toString())
//
//
//                        val userRef = database.child("AllData/Individual").child("key = ${i.key}").child(userId)
//                        userRef.setValue(alluser)
//                        sss = i.key.toString()
//                    }
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//        Toast.makeText(this,"$sss",Toast.LENGTH_SHORT).show()
    }

}