package com.skills.rentaride.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skills.rentaride.R
import com.skills.rentaride.model.LendTransactionDTO
import com.skills.rentaride.model.RentItemDTO

class RentItemListAdapter (var rentItem: ArrayList<RentItemDTO>) : RecyclerView.Adapter<RentItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RentItemViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)
    )

    /**
     * This function returns the total number of items in the list.
     */
    override fun getItemCount(): Int =  rentItem.size

    override fun onBindViewHolder(holder: RentItemViewHolder, position: Int) {
        holder.bindItems(rentItem[position])
    }

    fun updateLendTransactionHistory(newTransactions: List<RentItemDTO>){
        rentItem.clear()
        rentItem.addAll(newTransactions)
        notifyDataSetChanged()
    }
}