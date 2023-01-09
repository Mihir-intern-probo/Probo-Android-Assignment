package com.probo.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.probo.assignment.databinding.ActivityMainBinding

class splashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@splashActivity,HomeActivity::class.java)
            startActivity(intent);
            finish();
        },2000)
    }
}