package com.probo.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class LoggedIn : AppCompatActivity() {

    private lateinit var btnImg:Button;
    private lateinit var selectImg: ImageView;
    private var IMAGE_REQUEST_CODE = 100;
    private lateinit var tvEmail : TextView
    private lateinit var tvPassword: TextView
    private lateinit var tvDob : TextView
    private lateinit var logout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE);
        supportActionBar?.hide();

        btnImg = findViewById(R.id.pick_image);
        selectImg = findViewById(R.id.img_save);
        tvEmail = findViewById(R.id.tv_email)
        tvDob = findViewById(R.id.tv_dob)
        tvPassword = findViewById(R.id.tv_pass);
        logout = findViewById(R.id.logout_btn);
        tvEmail.text = token.getString("loginemail","")
        tvPassword.text = token.getString("password","");
        tvDob.text = token.getString("date","");

        btnImg.setOnClickListener{
            pickImageGallery();
        }

        logout.setOnClickListener{
            var editor = token.edit();
            editor.putString("loginemail","").putString("password","").putString("date","");
            editor.commit();
            var intent = Intent(this,HomeActivity::class.java);
            startActivity(intent);
            finish();
        }
    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK);
        intent.type="image/*";
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            var token = getSharedPreferences("username", Context.MODE_PRIVATE);
            selectImg.setImageURI(data?.data)
        }
    }
}