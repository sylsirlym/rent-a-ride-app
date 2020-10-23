package com.skills.rentaride.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.skills.rentaride.model.RentItemDTO
import com.skills.rentaride.utils.getProgressDrawable
import com.skills.rentaride.utils.loadImage
import kotlinx.android.synthetic.main.item_history.view.*

class RentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Get the country name.
    private val itemType = view.itemType

//    // Get the image view.
    private val imageView = view.imageView

    private val serialNumber = view.serialNumber
    private val itemCost = view.lendItemCost
    private val itemStatus = view.lendItemState

    // Get the progress drawable.
    private val progressDrawable = getProgressDrawable(view.context)


    @SuppressLint("SetTextI18n")
    fun bindItems(rentItemDTO: RentItemDTO, clickListener: OnItemClickListener) {
        itemType.text = rentItemDTO.itemTypeEntity.lendItemTypeName
        serialNumber.text = rentItemDTO.serialNumber
        itemCost.text = "KES: "+rentItemDTO.itemTypeEntity.lendItemCost.toString()+"0"
        itemStatus.text = rentItemDTO.dateCreated
        var image = "https://cdn.onlinewebfonts.com/svg/img_570325.png"
        if (rentItemDTO.itemTypeEntity.lendItemTypeName.equals("Bicycle", true))
              image ="https://png.pngtree.com/png-clipart/20190516/original/pngtree-illustration-transport-bicycle-bicyclemountain-bikebicycleillustration-bicycle-png-image_4006511.jpg"
        if (rentItemDTO.itemTypeEntity.lendItemTypeName.equals("Skate Board", true))
            image ="https://www.pikpng.com/pngl/m/46-464725_19-skateboard-vector-royalty-free-download-printable-skateboard.png"

        imageView.loadImage(image, progressDrawable)

        itemView.setOnClickListener {
            clickListener.onItemClicked(rentItemDTO)
        }
    }
}
interface OnItemClickListener{
     fun onItemClicked(rentItemDTO: RentItemDTO)
}