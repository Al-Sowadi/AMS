package com.abdullah.ams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth




class LogIn : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val singIn = findViewById<Button>(R.id.singIn)
        var eMail = findViewById<TextInputEditText>(R.id.eMail)
        var passwords = findViewById<TextInputEditText>(R.id.passwords)

        singIn.setOnClickListener{
            when{
                TextUtils.isEmpty(eMail.text.toString().trim{ it <= ' '})->{
                    Toast.makeText(this,"Please Enter email..",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(passwords.text.toString().trim{ it <= ' '})->{
                    Toast.makeText(this,"Please Enter password..",Toast.LENGTH_SHORT).show()
                }
                else->{
                    val email:String = eMail.text.toString().trim{it <= ' '}
                    val password:String = passwords.text.toString().trim{it <= ' '}
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener{task ->
                            if (task.isSuccessful){
                                Intent(this,MainActivity::class.java).also {
                                    startActivity(it)
                                }
                                Toast.makeText(this,"You are logged in successfully",Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                            }

                        }
                }
            }
        }
    }
}