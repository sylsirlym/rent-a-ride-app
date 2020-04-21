package com.skills.rentaride.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.LendTransactionDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class ListViewModel : ViewModel() {

    // Create a mutable list of countries.
    val transactionHistory = MutableLiveData<List<LendTransactionDTO>>()

    // Whether their is an error loading the country from the api.
    val transactionHistoryError = MutableLiveData<Boolean>()

    // Get loader state.
    val loading = MutableLiveData<Boolean>()

    // Get the countries service.
    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create().inject(this)
    }

    // Get the disposable object
    private val disposable = CompositeDisposable()

    /**
     * This function refreshes list of countries.
     */
    fun refresh(msisdn : String) {
        this.fetchTransactionHistory(msisdn)
    }

    /**
     * This function fetches countries.
     */
    private fun fetchTransactionHistory(msisdn: String) {
        loading.value = true
        /*
         We call the countries service on a different thread
         which is non-blocking and get notified when the api responds.
         */
        disposable.add(
            rentARideService.getLendTransactionHistory(msisdn)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseDTO>(){
                    override fun onSuccess(value: ResponseDTO?) {
                        transactionHistory.value = value?.data?.filterIsInstance<LendTransactionDTO>()
                        transactionHistoryError.value = false
                        loading.value = false
                    }
                    override fun onError(e: Throwable?) {
                        transactionHistoryError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
