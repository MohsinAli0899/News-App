package com.ali.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var etEmailAddress: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnSignUp: TextView

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null){
            Toast.makeText(this, "User is already logged in", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            login()
        }
        btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val email: String = etEmailAddress.text.toString()
        val password = etPassword.text.toString()

        if (email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Email or Password can't be blank", Toast.LENGTH_SHORT).show()
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
                
            }
    }
    private fun redirect(name: String){
        val intent = when(name){
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("No path Exist")
        }
        startActivity(intent)
        finish()

    }
}