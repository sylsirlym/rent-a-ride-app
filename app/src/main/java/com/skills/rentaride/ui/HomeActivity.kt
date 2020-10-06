package com.skills.rentaride.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import butterknife.OnClick
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.FragmentHomeBinding
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.LendTransactionDTO
import com.skills.rentaride.model.ProfileDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.ui.adapter.LendHistoryListAdapter
import com.skills.rentaride.utils.SharedPrefManager
import com.skills.rentaride.viewmodel.ListViewModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(){
    private val TAG = "HomeActivity"
    @Inject
    lateinit var rentARideService: RentARideService

    @BindView(R.id.lay_tab_home)
    var mTabHomeLayout: LinearLayout? = null

    @BindView(R.id.transaction_history)
    var mTabTransactionLayout: LinearLayout? = null

    @BindView(R.id.lay_tab_rent)
    var mTabCardLayout: LinearLayout? = null

    @BindView(R.id.lay_tab_help)
    var mTabHelpLayout: LinearLayout? = null

    @BindView(R.id.lay_tab_more)
    var mTabMoreLayout: LinearLayout? = null

//--------------------------------------------------------------------------------------------------
    var mImgHomeDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_home)

    var mImgTransactionDot: AppCompatImageView? = findViewById(R.id.img_transaction_dot_history)

    var mImgCardDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_rent)

    var mImgHelpDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_help)

    var mImgMoreDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_more)

//--------------------------------------------------------------------------------------------------
    @BindView(R.id.img_tab_home)
    var mImgHome: AppCompatImageView? = null

    @BindView(R.id.img_transaction_history)
    var mImgTransactionHistory: AppCompatImageView? = null

    @BindView(R.id.img_tab_rent)
    var mImgRent: AppCompatImageView? = null

    @BindView(R.id.img_tab_help)
    var mImgHelp: AppCompatImageView? = null

    @BindView(R.id.img_tab_more)
    var mImgMore: AppCompatImageView? = null

//--------------------------------------------------------------------------------------------------
    @BindView(R.id.txt_tab_home)
    var mHomeTextView: AppCompatTextView? = null

    @BindView(R.id.txt_transaction_history)
    var mTransactionTextView: AppCompatTextView? = null

    @BindView(R.id.txt_tab_search)
    var mCardTextView: AppCompatTextView? = null

    @BindView(R.id.txt_tab_help)
    var mHelpTextView: AppCompatTextView? = null

    @BindView(R.id.txt_tab_more)
    var mMoreTextView: AppCompatTextView? = null

//--------------------------------------------------------------------------------------------------
//    @BindView(R.id.txt_tab_notification)
//    var mNotificationTextView: AppCompatTextView? = null
//
//    @BindView(R.id.rl_toolbar)
//    var mHeaderToolBar: RelativeLayout? = null
//
//    @BindView(R.id.user_image)
//    var mUserImage: CircleImageView? = null
//
//    @BindView(R.id.appc_imv_flag)
//    var mLangImage: AppCompatImageView? = null
//
//    @BindView(R.id.img_option_arrow)
//    var mLangImageArrow: AppCompatImageView? = null
//
//    @BindView(R.id.iv_notification_dot)
//    var mNotificationDot: AppCompatImageView? = null

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
        changeBottomView(0)

        try {
            val profile: String = SharedPrefManager(this@HomeActivity).getSharedPrefManager(this@HomeActivity)!!.getString("profile")

//            val profile = intent.getStringExtra("profileDetails")
            val gson = Gson()
            val profileDetails = gson.fromJson(profile, ProfileDTO::class.java)
            //Bind Profile Details
            binding.profile=profileDetails

            //Fetch Transaction History
            val responseDTO:Single<ResponseDTO> = rentARideService.getLendTransactionHistory(profileDetails.msisdn)
            val histList=responseDTO.blockingGet().data?.filterIsInstance<LendTransactionDTO>()
            Log.i(TAG, "About to print")
            //Pass to Layout
            transact_grid_view.layoutManager = LinearLayoutManager(this)
            transact_grid_view.adapter = LendHistoryListAdapter(histList as ArrayList<LendTransactionDTO>)

            val transHist = findViewById<LinearLayout>(R.id.transaction_history)
            transHist.setOnClickListener {
                changeBottomView(1)
            }

//            Log.i(TAG, "Before View Model")
//            viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
//            viewModel.refresh(profileDetails.msisdn)
//
//            Log.i(TAG, "Before apply")
//            transact_grid_view.apply {
//                layoutManager = LinearLayoutManager(context)
//                adapter = lendHistoryListAdapter
//            }
//            Log.i(TAG, "Before setOnRefreshListener")
//            swiperefresh.setOnRefreshListener {
//                swiperefresh.isRefreshing = false
//                viewModel.refresh(profileDetails.msisdn)
//            }
//            Log.i(TAG, "Before observeViewModel")
//
//            this.observeViewModel()

        } catch (e: Exception){
            Log.e(TAG,"Got Error"+e.message)
            Log.wtf(TAG,e)
            changeBottomView(0)
        }

    }

    @OnClick(*[R.id.lay_tab_home, R.id.transaction_history, R.id.lay_tab_rent, R.id.lay_tab_help, R.id.lay_tab_more])
    fun onClick(view: View) {
        when (view.id) {
            R.id.lay_tab_home -> {
                changeBottomView(0)
            }
            R.id.transaction_history -> changeBottomView(1)
            R.id.lay_tab_rent -> changeBottomView(2)
            R.id.lay_tab_help -> changeBottomView(3)
            R.id.lay_tab_more -> changeBottomView(4)
        }
    }

//    private fun observeViewModel() {
//        viewModel.transactionHistory.observe(this, Observer { lendTransaction ->
//            lendTransaction?.let {
//                transact_grid_view.visibility = View.VISIBLE
//                lendHistoryListAdapter.updateLendTransactionHistory(it) }
//        })
//        viewModel.transactionHistoryError.observe(this, Observer { isError ->
//            isError?.let {
//                list_error.visibility = if(it) View.VISIBLE else View.GONE
//            }
//        })
//        viewModel.loading.observe(this, Observer {isLoading ->
//            isLoading?.let{
//                loading_view.visibility = if(it) View.VISIBLE else View.GONE
//                if(it){
//                    list_error.visibility = View.GONE
//                    transact_grid_view.visibility = View.GONE
//                }
//            }
//        })
//    }

    private fun changeBottomView(index: Int) {
        when (index) {
            0 -> {
                mImgHomeDot!!.visibility = View.INVISIBLE
                mImgTransactionDot!!.visibility = View.INVISIBLE
                mImgCardDot!!.visibility = View.INVISIBLE
                mImgHelpDot!!.visibility = View.INVISIBLE
                mImgMoreDot!!.visibility = View.INVISIBLE
                mHomeTextView!!.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome!!.setImageResource(R.drawable.ic_home_selected)
                mImgTransactionHistory!!.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent!!.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp!!.setImageResource(R.drawable.ic_help)
                mImgMore!!.setImageResource(R.drawable.ic_more)
            }
            1 -> {
                mImgHomeDot!!.visibility = View.INVISIBLE
                mImgTransactionDot!!.visibility = View.INVISIBLE
                mImgCardDot!!.visibility = View.INVISIBLE
                mImgHelpDot!!.visibility = View.INVISIBLE
                mImgMoreDot!!.visibility = View.INVISIBLE
                mHomeTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mCardTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome!!.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory!!.setImageResource(R.drawable.ic_baseline_history_24_selected)
                mImgRent!!.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp!!.setImageResource(R.drawable.ic_help)
                mImgMore!!.setImageResource(R.drawable.ic_more)
            }
            2 -> {
                mImgHomeDot!!.visibility = View.INVISIBLE
                mImgTransactionDot!!.visibility = View.INVISIBLE
                mImgCardDot!!.visibility = View.INVISIBLE
                mImgHelpDot!!.visibility = View.INVISIBLE
                mImgMoreDot!!.visibility = View.INVISIBLE
                mHomeTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView!!.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mHelpTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome!!.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory!!.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent!!.setImageResource(R.drawable.ic_baseline_directions_bike_24_selected)
                mImgHelp!!.setImageResource(R.drawable.ic_help)
                mImgMore!!.setImageResource(R.drawable.ic_more)
            }
            3 -> {
                mImgHomeDot!!.visibility = View.INVISIBLE
                mImgTransactionDot!!.visibility = View.INVISIBLE
                mImgCardDot!!.visibility = View.INVISIBLE
                mImgHelpDot!!.visibility = View.INVISIBLE
                mImgMoreDot!!.visibility = View.INVISIBLE
                mHomeTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView!!.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mMoreTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome!!.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory!!.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent!!.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp!!.setImageResource(R.drawable.ic_help_selected)
                mImgMore!!.setImageResource(R.drawable.ic_more)
            }
            4 -> {
                mImgHomeDot!!.visibility = View.INVISIBLE
                mImgTransactionDot!!.visibility = View.INVISIBLE
                mImgCardDot!!.visibility = View.INVISIBLE
                mImgHelpDot!!.visibility = View.INVISIBLE
                mImgMoreDot!!.visibility = View.INVISIBLE
                mHomeTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView!!.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mImgHome!!.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory!!.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent!!.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp!!.setImageResource(R.drawable.ic_help)
                mImgMore!!.setImageResource(R.drawable.ic_more_selected)
            }
        }
    }
}