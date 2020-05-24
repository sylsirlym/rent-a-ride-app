package com.skills.rentaride.ui

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.skills.rentaride.R
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.utils.SharedPrefManager
import io.reactivex.Single
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    // Get the countries service.
    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ///////// Temp Fix For android.os.NetworkOnMainThreadException at android.os.StrictMode$AndroidBlockGuardPolicy.onNetwork
        //Fix = https://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // finding the edit text
        val msisdnVar = findViewById<EditText>(R.id.editMsisdn)
        val nextBut = findViewById<Button>(R.id.nextButton)

        // Setting On Click Listener
        nextBut.setOnClickListener {
            val text = msisdnVar.text.toString()
            if (!isValidMsisdn(text)) {
                msisdnVar.error = "Invalid Mobile Number"
            } else {
                val msisdn = "254$text"
                SharedPrefManager(this@MainActivity).getSharedPrefManager(this@MainActivity)!!
                    .setString("msisdn", msisdn)
                val dto: Single<ResponseDTO>
                // Getting the user input
                dto = rentARideService.getProfile(msisdn)
                val resp = dto.blockingGet().statusMessage
                val code = dto.blockingGet().statusCode
                val data = dto.blockingGet().data
                //Wahala Here
                Log.i(TAG, "Got here")
                val prof = data?.get(0)

                if (code == 200 || code == 213) {
                    try {
                        Log.i(TAG, "Inside here")
                        SharedPrefManager(this@MainActivity).getSharedPrefManager(this@MainActivity)!!
                            .setString("profile", prof.toString())
                        val intent = Intent(baseContext, HomeActivity::class.java)
                        Log.i(TAG, "Inside here 1")
                        Log.i(TAG, "About to call Home Activity")

                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e(TAG, "Got Error" + e.message)
                        Log.wtf(TAG, e)

                    }
                } else {
                    try {
                        Log.i(TAG, "Use is not registered")
                        val intent = Intent(baseContext, RegisterActivity::class.java)
                        Log.i(TAG, "Inside here 1")

                        intent.putExtra("msisdn", text.toString())
                        Log.i(TAG, "About to call Register Activity")

                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e(TAG, "Got Error" + e.message)
                        Log.wtf(TAG, e)

                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isValidMsisdn(msisdn: String): Boolean {
        val MSISDN_REGEX = ("^[0-9]{9}?\$")
        val pattern: Pattern = Pattern.compile(MSISDN_REGEX)
        val matcher: Matcher = pattern.matcher(msisdn)
        return matcher.matches()
    }

}