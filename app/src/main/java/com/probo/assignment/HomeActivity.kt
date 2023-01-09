package com.probo.assignment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.regex.Pattern

// FIX BUTTON enable and disable thingy

class HomeActivity : AppCompatActivity() {

    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun isPasswordValid(password: String):Boolean{
        return Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$").matcher(password).matches();
    }

    private fun getYears(year: Int,month: Int,day: Int): Int {
        var diff = Calendar.getInstance().get(Calendar.YEAR) - year
        if (month > Calendar.getInstance().get(Calendar.MONTH) ||
            month == Calendar.getInstance().get(Calendar.MONTH) && day > Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ) {
            diff--
        }
        return diff
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportActionBar?.hide()

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        if(token.getString("loginemail","")!=""){
            val intent = Intent(this,LoggedIn::class.java)
            startActivity(intent);
            finish()
        }
        val email = findViewById<EditText>(R.id.ptEmail);
        val password = findViewById<EditText>(R.id.ptPassword);
        val confirmPassword = findViewById<EditText>(R.id.ptConfirmPassword);
        val date = findViewById<EditText>(R.id.ptDate);
        val submitButton = findViewById<Button>(R.id.btnSignUp);

        var year = Calendar.getInstance().get(Calendar.YEAR);
        var month = Calendar.getInstance().get(Calendar.MONTH);
        var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        submitButton.setOnClickListener{
            if(email.text.toString().equals("") || password.text.toString().equals("") || confirmPassword.text.toString().equals("") || date.text.toString().equals("")){
                Toast.makeText(getApplicationContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();
            }else if(!isValidEmailId(email.text.toString())){
                    Toast.makeText(getApplicationContext(),"Please enter the email of correct type",Toast.LENGTH_SHORT).show();
            }else if(!password.text.toString().equals(confirmPassword.text.toString())){
                Toast.makeText(getApplicationContext(), "Password and Confirm Password must be the same!", Toast.LENGTH_SHORT).show();
            }else if(getYears(year, month, day)<18){
                Toast.makeText(getApplicationContext(),"Must be 18 years old!",Toast.LENGTH_SHORT).show();
            }else if(!isPasswordValid(password.text.toString())) {
                Toast.makeText(getApplicationContext(),"Password must contain atleast one Capital Letter, one Small Letter, one Number" +
                        ", one symbol and should be atleast 8 characters long",Toast.LENGTH_SHORT).show();
            }else {
                val intent = Intent(this,LoggedIn::class.java);
                var editor = token.edit();
                editor.putString("loginemail", email.text.toString())
                    .putString("password", password.text.toString())
                    .putString("date", date.text.toString());
                editor.commit();
                startActivity(intent);
                finish();
            }
        }
        val calender = findViewById<ImageView>(R.id.calender);
        calender.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay->
                date.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear)
                year=mYear
                month=mMonth
                day=mDay
            },year,month,day);
            dpd.show();
        }

    }
}