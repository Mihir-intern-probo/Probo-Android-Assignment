package com.probo.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.probo.assignment.databinding.ActivityLoggedInBinding

class LoggedIn : AppCompatActivity() {

    private lateinit var binding: ActivityLoggedInBinding
    private var IMAGE_REQUEST_CODE = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater);

        setContentView(binding.root)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE);
        supportActionBar?.hide();

        binding.tvEmail.text = token.getString("loginemail","")
        binding.tvPass.text = token.getString("password","");
        binding.tvDob.text = token.getString("date","");

        binding.pickImage.setOnClickListener{
            pickImageGallery();
        }

        binding.logoutBtn.setOnClickListener{
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
            binding.imgSave.setImageURI(data?.data)
        }
    }
}