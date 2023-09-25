package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.testapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.net.ssl.SSLSessionBindingListener

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set view binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(checkAllField()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            auth.signOut()
                            Toast.makeText(this, "Account created successfully!",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    else{
                        //account not created succesfully
                            Log.e("error: ",it.exception.toString())
                        }
                }
            }
        }

// Function to check All the fields in the Sign Up page
    }
    private fun checkAllField(): Boolean {
        val email = binding.etEmail.text.toString()
        if(binding.etEmail.text.toString() == ""){
            binding.textInputLayoutEmail.error = "This is a required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        }
        //also not password should be atleast 8 characters
        if(binding.etPassword.text.toString() == ""){
            binding.textInputLayoutPassword.error = "This is required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        if(binding.etPassword.length() < 8){
            binding.textInputLayoutPassword.error = "The Password should be atleast 8 characters"
            binding.textInputLayoutPassword.errorIconDrawable = null
        }

        if(binding.etConfirmPassword.text.toString() == ""){
            binding.textInputLayoutConfirmPassword.error = "This is a required field"
            binding.textInputLayoutConfirmPassword.errorIconDrawable = null
            return false
        }
        if(binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()){
            binding.textInputLayoutConfirmPassword.error = "Passwords don't match"
            return false
        }
        return true
    }
}