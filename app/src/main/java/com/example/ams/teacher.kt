package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abdulllah.ams.All
import com.example.ams.Individual
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class teacher : AppCompatActivity() {
    private lateinit var drawerLayoutT: DrawerLayout
    private lateinit var actionBarToggleT: ActionBarDrawerToggle
    private lateinit var navViewT: NavigationView
    private var selectedItemOpT: String = "Student"
    lateinit var databaseReferenceT: DatabaseReference
    lateinit var justAmalT: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)
        drawerLayoutT = findViewById(R.id.drawerLayoutT)

        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggleT = ActionBarDrawerToggle(this, drawerLayoutT, 0, 0)
        drawerLayoutT.addDrawerListener(actionBarToggleT)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggleT.syncState()


        // Call findViewById on the NavigationView

        navViewT = findViewById(R.id.navViewT)

        justAmalT = findViewById(R.id.justAmalT)
        justAmalT.visibility = View.INVISIBLE
        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        navViewT.setNavigationItemSelectedListener { menuItem ->


            when (menuItem.itemId) {

                R.id.myProfile -> {


                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    Intent(this, Profile::class.java).also {
                        it.putExtra("id","teacher")
                        startActivity(it)
                    }
                    true
                }
                R.id.Listuser -> {
                    Toast.makeText(this, "users", Toast.LENGTH_SHORT).show()
                    Intent(this, ListUser::class.java).also {
                        it.putExtra("id","teacher")
                        startActivity(it)
                    }
                    true
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                    Intent(this,LogIn::class.java).also { startActivity(it) }
                    finish()
                    true
                }
                R.id.nav_Notification -> {
                    Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                    Intent(this,teacher_notiv::class.java).also { startActivity(it) }

                    true
                }
                else -> {
                    false
                }
            }
        }
        //
        val layoutInd = findViewById<LinearLayout>(R.id.layoutIndT)

        layoutInd.visibility = View.INVISIBLE
        val sendNote = findViewById<Button>(R.id.sendNoteT)
        val mySpinner: Spinner = findViewById(R.id.spinnerT)

        val items = arrayOf("All","Student", "Individual")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        mySpinner.adapter = adapter
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedItemOpT = selectedItem
                if (selectedItemOpT == "Individual") {
                    layoutInd.visibility = View.VISIBLE
                } else {
                    layoutInd.visibility = View.INVISIBLE
                }
                Toast.makeText(
                    applicationContext,
                    "Selected item: $selectedItemOpT",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)
        progressBar2?.visibility = View.INVISIBLE
        sendNote.setOnClickListener {
            progressBar2?.visibility = View.VISIBLE
            var TitleSendNote = findViewById<EditText>(R.id.TitleSendNoteT)
            var messageSendNote = findViewById<EditText>(R.id.messageSendNoteT)
            var AuserIdIndT = findViewById<EditText>(R.id.AuserIdIndT)
            val database = Firebase.database.reference
            val userId = UUID.randomUUID().toString()

            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val currentTimeF =  currentDateTime.format(formatter)

            val currentDate = LocalDate.now()
            val alluser = All(
                TitleSendNote.text.toString(),
                messageSendNote.text.toString(),
                currentTimeF.toString(),
                currentDate.toString()
            )
            if (selectedItemOpT == "All") {
                val userRef = database.child("AllData/All").child(userId)
                userRef.setValue(alluser)
            } else if (selectedItemOpT == "Student") {
                val userRef = database.child("AllData/FromStuff").child(userId)
                userRef.setValue(alluser)

            } else if (selectedItemOpT == "Individual") {
                val AuserIdIndT = findViewById<TextInputEditText>(R.id.AuserIdIndT)
                ///user
                databaseReferenceT = FirebaseDatabase.getInstance().getReference("AllData/User")
                databaseReferenceT.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (i in snapshot.children){
                            val ss = i.child("email").getValue().toString()
                            Toast.makeText(applicationContext,"done ${AuserIdIndT.text.toString()} $ss",Toast.LENGTH_SHORT).show()
                            if(ss == AuserIdIndT.text.toString()){
                                val database = Firebase.database.reference
                                val userId = UUID.randomUUID().toString()
                                val currentTime = LocalDate.now()
                                val alluser = Individual(TitleSendNote.text.toString(),messageSendNote.text.toString(),currentTimeF.toString(),userId, currentTime.toString())
                                val userRef = database.child("AllData/Individual").child("${i.key}").child(userId)
                                userRef.setValue(alluser)
                                Toast.makeText(applicationContext,"done yyyy",Toast.LENGTH_SHORT).show()
                                TitleSendNote.text = null
                                messageSendNote.text = null
                                AuserIdIndT.text = null
                                progressBar2?.visibility = View.GONE
                            }

                        }
                        progressBar2?.visibility = View.GONE
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
                /// user end

                Toast.makeText(this, "Yes ${AuserIdIndT.text.toString()} and", Toast.LENGTH_SHORT).show()
            }

//
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        drawerLayoutT.openDrawer(navViewT)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayoutT.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayoutT.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}