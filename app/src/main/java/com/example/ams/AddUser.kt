package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class AddUser : AppCompatActivity() {

    lateinit var textGander: String
    lateinit var textLevel: String
    private var progressBar: ProgressBar? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        var actionBar1 = getSupportActionBar()

        if (actionBar1 != null) {

            // Customize the back button
            actionBar1.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

            // showing the back button in action bar
            actionBar1.setDisplayHomeAsUpEnabled(true);
        }

        val add_userbtn = findViewById<Button>(R.id.add_userbtn)
        val Add_First_Name = findViewById<TextInputEditText>(R.id.Add_First_Name)
        val Add_last_Name = findViewById<TextInputEditText>(R.id.Add_last_Name)
        val Add_passwords = findViewById<TextInputEditText>(R.id.Add_passwords)
        val Add_eMail = findViewById<TextInputEditText>(R.id.Add_eMail)
        val Add_repasswords = findViewById<TextInputEditText>(R.id.Add_repasswords)
        val Add_age = findViewById<TextInputEditText>(R.id.Add_age)
        val radio_group_register_gender = findViewById<RadioGroup>(R.id.radio_group_register_gender)
        val radio_group_register_Level = findViewById<RadioGroup>(R.id.radio_group_register_Level)

        radio_group_register_gender.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            textGander = radioButton.text.toString()
            Toast.makeText(this, "Selected: ${radioButton.text}", Toast.LENGTH_SHORT).show()
        }
        radio_group_register_Level.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            textLevel = radioButton.text.toString()
            Toast.makeText(this, "Selected: ${radioButton.text}", Toast.LENGTH_SHORT).show()
        }

        progressBar = findViewById(R.id.progressBar)
        progressBar?.visibility = View.INVISIBLE
        add_userbtn.setOnClickListener {
            when {
                TextUtils.isEmpty(Add_First_Name.text.toString().trim { it <= ' ' }) -> {
                    Add_First_Name.setError("Please Enter First Name")
                    Add_First_Name.requestFocus()
                }
                TextUtils.isEmpty(Add_last_Name.text.toString().trim { it <= ' ' }) -> {
                    Add_last_Name.setError("Please Enter Last Name")
                    Add_last_Name.requestFocus()
                }
                TextUtils.isEmpty(Add_age.text.toString().trim { it <= ' ' }) -> {
                    Add_age.setError("Please Enter Age")
                    Add_age.requestFocus()
                }
                TextUtils.isEmpty(Add_eMail.text.toString().trim { it <= ' ' }) -> {
                    Add_eMail.setError( "Please Enter email..")
                    Add_eMail.requestFocus()
                }
                TextUtils.isEmpty(Add_passwords.text.toString().trim { it <= ' ' }) -> {
                    Add_passwords.setError( "Please Enter password..")
                    Add_passwords.requestFocus()
                }
                TextUtils.isEmpty(Add_repasswords.text.toString().trim { it <= ' ' }) -> {
                    Add_repasswords.setError( "Please Enter repassword..")
                    Add_repasswords.requestFocus()
                }
                else -> {

                    var str_Add_First_Name = Add_First_Name.text.toString().trim { it <= ' ' }
                    var str_Add_last_Name = Add_last_Name.text.toString().trim { it <= ' ' }
                    var str_Add_passwords = Add_passwords.text.toString().trim { it <= ' ' }
                    var str_Add_eMail = Add_eMail.text.toString().trim { it <= ' ' }
                    var str_Add_repasswords = Add_repasswords.text.toString().trim { it <= ' ' }
                    var str_Add_age = Add_age.text.toString().trim { it <= ' ' }
                    if(textGander == null){
                       Toast.makeText(this,"Please chose the Gander",Toast.LENGTH_LONG).show()
                    }else if(textLevel == null){
                        Toast.makeText(this,"Please chose the Level",Toast.LENGTH_LONG).show()
                    }else if(!str_Add_passwords.equals(str_Add_repasswords)){
                        Toast.makeText(this,"Password does not match",Toast.LENGTH_LONG).show()
                    }else
                    {
                        progressBar?.visibility = View.VISIBLE
                        registerUser(str_Add_First_Name,str_Add_last_Name,str_Add_passwords,str_Add_eMail,str_Add_age,textGander,textLevel)
                        Add_First_Name.text = null
                        Add_last_Name.text = null
                        Add_age.text = null
                        Add_passwords.text = null
                        Add_repasswords.text = null
                        Add_eMail.text = null

                    }
                }

            }
        }

    }

    private fun registerUser(strAddFirstName: String, strAddLastName: String, strAddPasswords: String, strAddEmail: String, strAddAge: String, textGander: String, textLevel: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(strAddEmail, strAddPasswords)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val Userobj = user(
                        strAddFirstName,
                        strAddLastName,
                        strAddAge,
                        textGander,
                        textLevel,
                        strAddEmail
                    )
                    FirebaseAuth.getInstance().currentUser?.uid?.let {
                        FirebaseDatabase.getInstance().getReference("AllData/User")
                            .child(
                                it
                            ).setValue(Userobj).addOnCompleteListener { task ->
                                if (task.isSuccessful){

                                    Toast.makeText(
                                        this,
                                        "Use added successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    progressBar?.visibility = View.GONE
                                }else{
                                    Toast.makeText(
                                        this,
                                        "Use did not add successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    progressBar?.visibility = View.GONE
                                }
                            }
                    }
                }
                else {
                    Toast.makeText(
                        this,
                        "You are not add  successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar?.visibility = View.GONE
                }
            }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
//                Intent(this,teacher::class.java).also { startActivity(it) }
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}

