package com.abdullah.ams


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abdulllah.ams.All
import com.example.ams.Individual
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class Admin : AppCompatActivity() {
    // Initialise the DrawerLayout, NavigationView and ToggleBar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private var selectedItemOp: String = "All"
    lateinit var databaseReference: DatabaseReference
    lateinit var justAmal: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        drawerLayout = findViewById(R.id.drawerLayout)
        var strLevel: String? = null
        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()


        // Call findViewById on the NavigationView

        navView = findViewById(R.id.navView)

        justAmal = findViewById(R.id.justAmal)
        justAmal.visibility = View.INVISIBLE
        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        navView.setNavigationItemSelectedListener { menuItem ->


            when (menuItem.itemId) {

                R.id.myProfile -> {


                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    Intent(this, Profile::class.java).also {
                        it.putExtra("id","admin")
                        startActivity(it)
                    }
                    true
                }
                R.id.Listuser -> {
                    Toast.makeText(this, "users", Toast.LENGTH_SHORT).show()
                    Intent(this, ListUser::class.java).also {
                        it.putExtra("id","admin")
                        startActivity(it)
                    }
                    true
                }
                R.id.addusermu -> {
                    Toast.makeText(this, "Add user", Toast.LENGTH_SHORT).show()
                    Intent(this, AddUser::class.java).also {
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
                else -> {
                    false
                }
            }
        }
        //
        val layoutInd = findViewById<LinearLayout>(R.id.layoutInd)

        layoutInd.visibility = View.INVISIBLE
        val sendNote = findViewById<Button>(R.id.sendNote)
        val mySpinner: Spinner = findViewById(R.id.spinner)

        val items = arrayOf("All", "Student", "Teacher", "Individual")
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
                selectedItemOp = selectedItem
                if (selectedItemOp == "Individual") {
                    layoutInd.visibility = View.VISIBLE
                } else {
                    layoutInd.visibility = View.INVISIBLE
                }
                Toast.makeText(
                    applicationContext,
                    "Selected item: $selectedItemOp",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
       val progressBar1 = findViewById<ProgressBar>(R.id.progressBar1)
        progressBar1?.visibility = View.INVISIBLE
        sendNote.setOnClickListener {
            progressBar1?.visibility = View.VISIBLE
            var TitleSendNote = findViewById<EditText>(R.id.TitleSendNote)
            var messageSendNote = findViewById<EditText>(R.id.messageSendNote)
            var userIdInd = findViewById<EditText>(R.id.userIdInd)
            val database = Firebase.database.reference
            val userId = UUID.randomUUID().toString()
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val currentDate =  LocalDate.now()
            val currentTimeF = currentDateTime.format(formatter)

            val alluser = All(
                TitleSendNote.text.toString(),
                messageSendNote.text.toString(),
                currentTimeF.toString(),
                currentDate.toString()
            )

            if (selectedItemOp == "All") {
                val userRef = database.child("AllData/All").child(userId)
                userRef.setValue(alluser)
                progressBar1?.visibility = View.GONE
                TitleSendNote.text = null
                userIdInd.text = null
                messageSendNote.text = null
            } else if (selectedItemOp == "Student") {
                val userRef = database.child("AllData/Student").child(userId)
                userRef.setValue(alluser)
                progressBar1?.visibility = View.GONE
                TitleSendNote.text = null
                userIdInd.text = null
                messageSendNote.text = null
            } else if (selectedItemOp == "Teacher") {
                val userRef = database.child("AllData/ToStuff").child(userId)
                userRef.setValue(alluser)
                progressBar1?.visibility = View.GONE
                TitleSendNote.text = null
                userIdInd.text = null
                messageSendNote.text = null
            } else if (selectedItemOp == "Individual") {
                val userIdInd = findViewById<TextInputEditText>(R.id.userIdInd)
                ///user
                databaseReference = FirebaseDatabase.getInstance().getReference("AllData/User")
                databaseReference.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (i in snapshot.children){
                            val ss = i.child("email").getValue().toString()
                            if(ss == userIdInd.text.toString()){
                                val database = Firebase.database.reference
                                val userId = UUID.randomUUID().toString()

                                val alluser = Individual(TitleSendNote.text.toString(),messageSendNote.text.toString(),currentTimeF.toString(),userId, currentDate.toString())
                                val userRef = database.child("AllData/Individual").child("${i.key}").child(userId)
                                userRef.setValue(alluser)
                                progressBar1?.visibility = View.GONE
                                TitleSendNote.text = null
                                userIdInd.text = null
                                messageSendNote.text = null
                            }

                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
                /// user end

                Toast.makeText(this, "Submite ${userIdInd.text.toString()} and", Toast.LENGTH_SHORT).show()
            }


        }
//        progressBar1?.visibility = View.GONE
    }
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}