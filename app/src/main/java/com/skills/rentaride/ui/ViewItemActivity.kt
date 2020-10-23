package com.skills.rentaride.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import butterknife.ButterKnife
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.ActivityViewItemBinding
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ProfileDTO
import com.skills.rentaride.model.RentItemDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.utils.SharedPrefManager
import kotlinx.android.synthetic.main.activity_view_item.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import javax.inject.Inject


class ViewItemActivity : AppCompatActivity() {
    private val TAG = "ViewItemActivity"
    private lateinit var alertDialogButton: Button

    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewItemBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_view_item
        )
        ButterKnife.bind(this)

        try {
            val item = intent.getStringExtra("item")
            val itemDTO = parseResponse(item!!)
            binding.rentItem = itemDTO!!
            var imageUrl = "https://cdn.onlinewebfonts.com/svg/img_570325.png"
            if (itemDTO.itemTypeEntity.lendItemTypeName.equals("Bicycle", true))
                imageUrl =
                    "https://png.pngtree.com/png-clipart/20190516/original/pngtree-illustration-transport-bicycle-bicyclemountain-bikebicycleillustration-bicycle-png-image_4006511.jpg"
            if (itemDTO.itemTypeEntity.lendItemTypeName.equals("Skate Board", true))
                imageUrl =
                    "https://www.pikpng.com/pngl/m/46-464725_19-skateboard-vector-royalty-free-download-printable-skateboard.png"
            val newUrl = URL(imageUrl)
            val mIcon = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream())
            val image = this.imageView2
            image.setImageBitmap(mIcon)
            alertDialogButton = this.nextButton
            setupAlertDialogButton(itemDTO)
        } catch (ex: Exception) {
            Log.e("TAG", "Exception " + ex.message)
            Log.wtf("TAG", ex)
        }

    }

    private fun parseResponse(item: String): RentItemDTO? {
        Log.i(TAG, "Selected Item: $item")
        return Gson().fromJson(item, RentItemDTO::class.java)
    }

    private fun setupAlertDialogButton(itemDTO: RentItemDTO) {
        alertDialogButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Rent The Ride?")
                .setMessage("This will sent a rent request for ${itemDTO.itemTypeEntity.lendItemTypeName} with serial number ${itemDTO.serialNumber}")
                .setPositiveButton("Ok") { dialog, which ->
                    Toast.makeText(this, "Sending Request ...", Toast.LENGTH_SHORT).show()
                    sendApiCall(itemDTO.lendItemID)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(this, "Prompt Canceled", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    private fun sendApiCall(itemID: Int) {
        Log.i(TAG, "BUTTON CLICK###############################################")

        val profile: String =
            SharedPrefManager(this@ViewItemActivity).getSharedPrefManager(this@ViewItemActivity)!!
                .getString(
                    "profile"
                )

        val profileDetails = Gson().fromJson(profile, ProfileDTO::class.java)

        val json = JSONObject()
        json.put("lendItemID", itemID)
        json.put("profileID", profileDetails.profileID)

        Log.i(TAG, "Json:$json")
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), json.toString())

        rentARideService.rentItem(requestBody).enqueue(
            object : Callback<ResponseDTO> {
                @SuppressLint("CommitPrefEdits")
                override fun onResponse(
                    call: Call<ResponseDTO>?,
                    response: Response<ResponseDTO>?
                ) {
                    if (response!!.isSuccessful) {
                        Log.i(TAG, "Successful")
                    }
                    navigate(response)
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                    Log.i(TAG, "Failed to register")
                    Log.e(TAG, "Exception " + t.message)
                    Toast.makeText(
                        this@ViewItemActivity,
                        R.string.LBL_CHECK_CONNECTION,
                        Toast.LENGTH_SHORT
                    ).show();
                }
            })
    }

    private fun navigate(response: Response<ResponseDTO>) {
        try {
            val intent = Intent(baseContext, SuccessActivity::class.java)
            val jsonString = Gson().toJson(response.body())
            intent.putExtra("response", jsonString)
            Log.i(TAG, "Moving to SuccessActivity")
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Exception :" + e.message)
            Log.wtf(TAG, e)
        }
    }
}