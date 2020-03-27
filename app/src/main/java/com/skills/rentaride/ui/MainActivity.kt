package com.skills.rentaride.ui

import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skills.rentaride.R
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import io.reactivex.Single
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

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

        //Update the Hint
        msisdnVar.setOnClickListener{
            msisdnVar.setHint("254XXXXXXXXX")
        }

        // Setting On Click Listener
        nextBut.setOnClickListener {

            val dto : Single<ResponseDTO>
            // Getting the user input
            val text = msisdnVar.text
            dto = rentARideService.getProfile(text.toString())
            val resp = dto.blockingGet().statusMessage
            // Showing the user input
            Toast.makeText(this, resp, Toast.LENGTH_SHORT).show()
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
}