package com.skills.rentaride.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skills.rentaride.R
import com.skills.rentaride.model.LendTransactionDTO
import kotlinx.android.synthetic.main.item_history.view.*

class LendHistoryListAdapter (var lendTransactionHistory: ArrayList<LendTransactionDTO>) : RecyclerView.Adapter<LendHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LendHistoryViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)
    )

    /**
     * This function returns the total number of items in the list.
     */
    override fun getItemCount(): Int =  lendTransactionHistory.size

    override fun onBindViewHolder(holder: LendHistoryViewHolder, position: Int) {
        holder.bind(lendTransactionHistory[position])
    }

    fun updateLendTransactionHistory(newTransactions: List<LendTransactionDTO>){
        lendTransactionHistory.clear()
        lendTransactionHistory.addAll(newTransactions)
        notifyDataSetChanged()
    }
}