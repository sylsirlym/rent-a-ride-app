package com.skills.rentaride.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skills.rentaride.model.LendTransactionDTO
import com.skills.rentaride.utils.getProgressDrawable
import kotlinx.android.synthetic.main.item_history.view.*

class LendHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Get the country name.
    private val itemType = view.itemType

//    // Get the image view.
//    private val imageView = view.imageView

    // Get the country capital.
    private val serialNumber = view.serialNumber

    // Get the progress drawable.
    private val progressDrawable = getProgressDrawable(view.context)


    fun bindItems(lendTransactionDTO: LendTransactionDTO) {
        itemType.text = lendTransactionDTO.lendItemTypeName
        serialNumber.text = lendTransactionDTO.serialNumber
//        imageView.loadImage(country.flag,progressDrawable)
    }
}