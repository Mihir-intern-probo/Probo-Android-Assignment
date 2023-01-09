package com.probo.assignment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.probo.assignment.databinding.ActivitySecondBinding
import java.util.*
import java.util.regex.Pattern

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding;

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

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            binding.btnSignUp.setEnabled(!binding.ptEmail.text.trim().isEmpty() &&
                    !binding.ptPassword.text.trim().isEmpty() &&
                    !binding.ptConfirmPassword.text.trim().isEmpty() &&
                    !binding.ptDate.text.trim().isEmpty())
        }
        override fun afterTextChanged(p0: Editable?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater);

        setContentView(binding.root)
        supportActionBar?.hide()

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        if(token.getString("loginemail","")!=""){
            val intent = Intent(this,LoggedIn::class.java)
            startActivity(intent);
            finish()
        }

        binding.btnSignUp.setEnabled(false);

        var year = Calendar.getInstance().get(Calendar.YEAR);
        var month = Calendar.getInstance().get(Calendar.MONTH);
        var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // TextWatcher

        binding.ptEmail.addTextChangedListener(textWatcher);
        binding.ptPassword.addTextChangedListener(textWatcher);
        binding.ptConfirmPassword.addTextChangedListener(textWatcher);
        binding.ptDate.addTextChangedListener(textWatcher);

        // Submit button onCLicker listener

        binding.btnSignUp.setOnClickListener{
            if(!isValidEmailId(binding.ptEmail.text.toString())){
                    Toast.makeText(getApplicationContext(),"Please enter the email of correct type",Toast.LENGTH_SHORT).show();
            }else if(!binding.ptPassword.text.toString().equals(binding.ptConfirmPassword.text.toString())){
                Toast.makeText(getApplicationContext(), "Password and Confirm Password must be the same!", Toast.LENGTH_SHORT).show();
            }else if(getYears(year, month, day)<18){
                Toast.makeText(getApplicationContext(),"Must be 18 years old!",Toast.LENGTH_SHORT).show();
            }else if(!isPasswordValid(binding.ptPassword.text.toString())) {
                Toast.makeText(getApplicationContext(),"Password must contain atleast one Capital Letter, one Small Letter, one Number" +
                        ", one symbol and should be atleast 8 characters long",Toast.LENGTH_SHORT).show();
            }else {
                val intent = Intent(this,LoggedIn::class.java);
                var editor = token.edit();
                editor.putString("loginemail", binding.ptEmail.text.toString())
                    .putString("password", binding.ptPassword.text.toString())
                    .putString("date", binding.ptDate.text.toString());
                editor.commit();
                startActivity(intent);
                finish();
            }
        }

        // Calendar function


        binding.calender.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay->
                binding.ptDate.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear)
                year=mYear
                month=mMonth
                day=mDay
            },year,month,day);
            dpd.show();
        }
    }
}