package com.abdullah.ams

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.ams.R
import com.example.ams.databinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class NewNotification : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val viewNew =  inflater.inflate(R.layout.fragment_new_notification, container, false)
        val mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        val uid = currentUser?.uid
        val databaseNew = FirebaseDatabase.getInstance()
        val myRefNew = databaseNew.getReference("AllData/Individual/$uid")
//        val userRef = database.child("AllData/All").child(userId)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val arr = ArrayList<String>()
        for (i in 0..5) {
            val date = LocalDate.now().minusDays(i.toLong())
            arr.add(formatter.format(date))
//            println(formatter.format(date))
        }


        val recyclerviewNew = viewNew.findViewById<RecyclerView>(R.id.recyclerviewNew)
        recyclerviewNew?.layoutManager = LinearLayoutManager(viewNew.context)

        val dataNew = ArrayList<databinding>()

        var getdataNew = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var m1New = i.child("title").getValue()
                    var m2New = i.child("message").getValue()
                    var m3New = i.child("time").getValue()
                    var idDate = i.child("id").getValue()
                    if(idDate.toString() == arr[0] || idDate.toString() == arr[1] ||idDate.toString() == arr[2] ||idDate.toString()==arr[3] || idDate.toString()==arr[4]) {

                        dataNew.add(
                            databinding(
                                m1New.toString(),
                                m2New.toString(),
                                m3New.toString()
                            )
                        )
                    }
                }
                val adapterNew = fargmentadapter(dataNew)

                // Setting the Adapter with the recyclerview
                recyclerviewNew?.adapter = adapterNew
            }
        }
        myRefNew.addValueEventListener(getdataNew)
//        myRefNew.addListenerForSingleValueEvent(getdataNew)


        return viewNew
    }

}