package com.skills.rentaride.ui

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import butterknife.ButterKnife
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.ActivityViewItemBinding
import com.skills.rentaride.model.RentItemDTO
import kotlinx.android.synthetic.main.activity_view_item.*
import java.net.URL


class ViewItemActivity : AppCompatActivity() {
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
                imageUrl ="https://png.pngtree.com/png-clipart/20190516/original/pngtree-illustration-transport-bicycle-bicyclemountain-bikebicycleillustration-bicycle-png-image_4006511.jpg"
            if (itemDTO.itemTypeEntity.lendItemTypeName.equals("Skate Board", true))
                imageUrl ="https://www.pikpng.com/pngl/m/46-464725_19-skateboard-vector-royalty-free-download-printable-skateboard.png"
            val newUrl = URL(imageUrl)
            val mIcon = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream())
            val image = this.imageView2
            image.setImageBitmap(mIcon)
        } catch (ex: Exception){
            Log.e("TAG", "Exception " + ex.message)
            Log.wtf("TAG", ex)
        }

    }

    private fun parseResponse(item: String): RentItemDTO? {
        Log.e("TAG", "Selected Item: $item")

        val gson = Gson()
        return gson.fromJson(item, RentItemDTO::class.java)
    }

}