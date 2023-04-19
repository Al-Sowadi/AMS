package com.abdullah.ams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.ams.R
import com.abdullah.ams.fargmentadapter
import com.example.ams.databinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ToTeacherNotification : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val tVteacher = inflater.inflate(R.layout.fragment_to_teacher_notification, container, false)

        val databaseToday = FirebaseDatabase.getInstance()
        val myRefToday = databaseToday.getReference("AllData/ToStuff")
//        val userRef = database.child("AllData/All").child(userId)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val arr = ArrayList<String>()
        for (i in 0..5) {
            val date = LocalDate.now().minusDays(i.toLong())
            arr.add(formatter.format(date))
//            println(formatter.format(date))
        }
        val recyclerviewToday = tVteacher.findViewById<RecyclerView>(R.id.recyclerviewTteacher)
        recyclerviewToday?.layoutManager = LinearLayoutManager(tVteacher.context)

        val dataToday = ArrayList<databinding>()

        var getdataToday = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var m1Today = i.child("title").getValue()
                    var m2Today = i.child("message").getValue()
                    var m3Today = i.child("time").getValue()
                    var idDate = i.child("id").getValue()
                    if(idDate.toString() == arr[0] || idDate.toString() == arr[1] ||idDate.toString() == arr[2] ||idDate.toString()==arr[3] || idDate.toString()==arr[4]){
                       dataToday.add(databinding(m1Today.toString(),m2Today.toString(),m3Today.toString()))
                    }
                }
                val adapterToday = fargmentadapter(dataToday)

                // Setting the Adapter with the recyclerview
                recyclerviewToday?.adapter = adapterToday
            }
        }
        myRefToday.addValueEventListener(getdataToday)


        return tVteacher
    }
}