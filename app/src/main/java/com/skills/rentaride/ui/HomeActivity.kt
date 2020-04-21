package com.skills.rentaride.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.FragmentHomeBinding
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ProfileDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.ui.adapter.LendHistoryListAdapter
import com.skills.rentaride.viewmodel.ListViewModel
import androidx.lifecycle.Observer
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(){
    private val TAG = "HomeActivity"
    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    private lateinit var viewModel: ListViewModel

    private val lendHistoryListAdapter = LendHistoryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_home)
        val binding: FragmentHomeBinding = DataBindingUtil.setContentView(
            this, R.layout.fragment_home)

        try {
            val profile = intent.getStringExtra("profileDetails")
            Log.i(TAG, profile)
            val gson = Gson()
            val profileDetails = gson.fromJson(profile, ProfileDTO::class.java)
            Log.i(TAG, profileDetails.fname)
            binding.profile=profileDetails;

            Log.i(TAG, "Before View Model")
            viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
            viewModel.refresh(profileDetails.msisdn)

            Log.i(TAG, "Before apply")
            transact_grid_view.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = lendHistoryListAdapter
            }
            Log.i(TAG, "Before setOnRefreshListener")
            swiperefresh.setOnRefreshListener {
                swiperefresh.isRefreshing = false
                viewModel.refresh(profileDetails.msisdn)
            }
            Log.i(TAG, "Before observeViewModel")

            this.observeViewModel()

        } catch (e: Exception){
            Log.e(TAG,"Got Error"+e.message)
            Log.wtf(TAG,e)

        }

    }

    private fun observeViewModel() {
        viewModel.transactionHistory.observe(this, Observer { lendTransaction ->
            lendTransaction?.let {
                transact_grid_view.visibility = View.VISIBLE
                lendHistoryListAdapter.updateLendTransactionHistory(it) }
        })
        viewModel.transactionHistoryError.observe(this, Observer { isError ->
            isError?.let {
                list_error.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(this, Observer {isLoading ->
            isLoading?.let{
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    transact_grid_view.visibility = View.GONE
                }
            }
        })
    }
}