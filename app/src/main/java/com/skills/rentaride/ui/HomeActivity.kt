package com.skills.rentaride.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.FragmentHomeBinding
import com.skills.rentaride.model.ProfileDTO

class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
        val binding: FragmentHomeBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_home)

        try {
            val profile = intent.getStringExtra("profileDetails")
            Log.i(TAG, profile)
            val gson = Gson()
            val profileDetails = gson.fromJson(profile, ProfileDTO::class.java)
            Log.i(TAG, profileDetails.fname)
            binding.profile=profileDetails;
        } catch (e: Exception){
            Log.e(TAG,"Got Error"+e.message)
            Log.wtf(TAG,e)

        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.profile_image)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.profile_image)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}