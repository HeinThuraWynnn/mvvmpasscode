package com.anubhav87.mvvmtutorial.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.anubhav87.mvvmtutorial.R
import com.anubhav87.mvvmtutorial.SharedPreference
import com.hanks.passcodeview.PasscodeView
import kotlinx.android.synthetic.main.activity_create__pin_code.*

class Create_Pincode : AppCompatActivity() {

    private lateinit var  passcodeView : PasscodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__pin_code)

        val sharedPreference: SharedPreference = SharedPreference(this)
        passcodeView = findViewById(R.id.passcodeView)
        passcodeView.listener = object : PasscodeView.PasscodeViewListener {

            override fun onFail() {
                Toast.makeText(getApplication(), "Wrong!!", Toast.LENGTH_SHORT).show()

            }

            override fun onSuccess(number: String) {
                Toast.makeText(getApplication(), "finish", Toast.LENGTH_SHORT).show()
//
//				val intent = Intent(this, MainActivit::class.java)
//				startActivity(intent)
//				finish()
                onBackPressed()
            }
        }

        skip_pc!!.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
