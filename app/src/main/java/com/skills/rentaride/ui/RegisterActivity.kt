package com.skills.rentaride.ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.skills.rentaride.R
import java.util.*


class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"
    val COUNTRIES =
        arrayOf("Registration Number", "Staff ID")

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.list_id_types,
            COUNTRIES
        )

        val idTypeDropDown: AutoCompleteTextView = findViewById(R.id.idType)
        idTypeDropDown.setAdapter(adapter)
        try {
            val dateField = findViewById<EditText>(R.id.date)
            //Date Picker
            val builder = MaterialDatePicker.Builder.datePicker()
            dateField.setOnClickListener{
                Log.d(TAG, "Date Picker")

                val picker = builder.build()

                picker.show(supportFragmentManager, picker.toString())
                picker.addOnNegativeButtonClickListener {
                    Log.d(TAG, "Dialog Negative Button was clicked")
                }

                picker.addOnPositiveButtonClickListener {
                    Log.d(TAG, "Date String = ${picker.headerText}:: Date epoch value = ${it}")
                }
            }
        } catch (e: Exception){
            Log.e(TAG,"Got Error"+e.message)
            Log.wtf(TAG,e)

        }


    }

}