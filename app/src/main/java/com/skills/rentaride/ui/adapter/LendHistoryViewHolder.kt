package com.skills.rentaride.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skills.rentaride.model.LendTransactionDTO
import com.skills.rentaride.utils.getProgressDrawable
import com.skills.rentaride.utils.loadImage
import kotlinx.android.synthetic.main.item_history.view.*

class LendHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

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
    fun bindItems(lendTransactionDTO: LendTransactionDTO) {
        itemType.text = lendTransactionDTO.lendItemTypeName
        serialNumber.text = lendTransactionDTO.serialNumber
        itemCost.text = "KES: "+lendTransactionDTO.lendItemCost.toString()+"0"
        itemStatus.text = lendTransactionDTO.lendTransactionStatus
        imageView.loadImage("https://www.flaticon.com/svg/static/icons/svg/3596/3596080.svg", progressDrawable)
    }
}