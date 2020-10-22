package com.skills.rentaride.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.utils.SharedPrefManager
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Callback
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"
    private val idDocs =
        arrayOf("Registration Number", "Staff ID")
    private var cal = Calendar.getInstance()


    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.list_id_types,
            idDocs
        )

        // DROPDOWN ---------------------------------------------------------------------------------
        val idTypeDropDown: AutoCompleteTextView = findViewById(R.id.idType)
        idTypeDropDown.setAdapter(adapter)

        // DATE PICKER ------------------------------------------------------------------------------
        val dateField = this.date
        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener {
                    view,
                    year,
                    monthOfYear,
                    dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(dateField)
            }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        dateField!!.setOnClickListener {
            DatePickerDialog(
                this@RegisterActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val regButton = findViewById<Button>(R.id.button)
        val fname = findViewById<EditText>(R.id.fname)
        val lname = findViewById<EditText>(R.id.lname)
        val email = findViewById<EditText>(R.id.email)
        val idNumber = findViewById<EditText>(R.id.idNumber)
//        val idNo = this.idNumber
        regButton.setOnClickListener{
            Log.i(TAG,"BUTTON CLICK###############################################")
            val json = JSONObject()
            json.put("msisdn",intent.getStringExtra("msisdn")!!)
            json.put("fname",fname.text.toString())
            json.put("otherNames",lname.text.toString())
            json.put("emailAddress",email.text.toString())
            json.put("dateOfBirth",dateField.text.toString())
            json.put("identificationNumber",idNumber.text.toString())
            json.put("identificationType",1)
           val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
            val responseDTO = rentARideService.createProfile(requestBody)
            responseDTO.enqueue(
                object : Callback<ResponseDTO> {
                    @SuppressLint("CommitPrefEdits")
                    override fun onResponse(call: Call<ResponseDTO>?, response: Response<ResponseDTO>?) {
                        if (response!!.isSuccessful) {
                            Log.i(TAG,"Succesful")
                        }
                        navigate(response)
                    }

                    override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                        Log.i(TAG,"Failed to register")
                        Log.e(TAG,"Expection " +t.message)
                        Toast.makeText(this@RegisterActivity, R.string.LBL_CHECK_CONNECTION, Toast.LENGTH_SHORT).show();
                    }
                })

        }

    }

    private fun updateDateInView(dateField: EditText) {
        val myFormat = "yyyy-MM-dd" // 2003-05-07
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateField.setText(sdf.format(cal.getTime()))
    }

    private fun navigate(response: Response<ResponseDTO>){
        try {
            val intent = Intent(baseContext, SuccessActivity::class.java)
            val gson = Gson()
            val jsonString = gson.toJson(response.body())
            intent.putExtra("response", jsonString)

            val prof = response.body()?.data!![0]
            SharedPrefManager(this@RegisterActivity).getSharedPrefManager(this@RegisterActivity)!!
                .setString("profile", prof.toString())
            Log.i(TAG,"Moving to SuccessActivity")
            startActivity(intent)
        } catch (e : Exception){
            Log.e(TAG,"Exception :"+e.message)
            Log.wtf(TAG, e)
        }
    }

}