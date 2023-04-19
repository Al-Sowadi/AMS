package com.abdullah.ams

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.ams.R
import com.example.ams.databinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AllNotification : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_all_notification, container, false)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("AllData/All")
//        val userRef = database.child("AllData/All").child(userId)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val arr = ArrayList<String>()
        for (i in 0..5) {
            val date = LocalDate.now().minusDays(i.toLong())
            arr.add(formatter.format(date))
//            println(formatter.format(date))
        }

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(view.context)

        val data = ArrayList<databinding>()

       var getdata = object : ValueEventListener{
           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

           override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var m1 = i.child("title").getValue()
                    var m2 = i.child("message").getValue()
                    var m3 = i.child("time").getValue()
                    var idDate = i.child("id").getValue()
                    if(idDate.toString() == arr[0] || idDate.toString() == arr[1] ||idDate.toString() == arr[2] ||idDate.toString()==arr[3] || idDate.toString()==arr[4]){
                        data.add(databinding(m1.toString(),m2.toString(),m3.toString()))
                    }


                }
               val adapter = fargmentadapter(data)

               // Setting the Adapter with the recyclerview
               recyclerview.adapter = adapter
           }
       }
//        myRef.addValueEventListener(getdata)
        myRef.addListenerForSingleValueEvent(getdata)




        return view
    }
}