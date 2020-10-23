package com.skills.rentaride.ui

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.FragmentRentBinding
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ProfileDTO
import com.skills.rentaride.model.RentItemDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.ui.adapter.OnItemClickListener
import com.skills.rentaride.ui.adapter.RentItemListAdapter
import com.skills.rentaride.ui.adapter.RentItemViewHolder
import com.skills.rentaride.utils.SharedPrefManager
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class RentActivity : AppCompatActivity(), OnItemClickListener {
    private val TAG = "RentActivity"
    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    @Inject
    lateinit var rentARideService: RentARideService

    @BindView(R.id.lay_tab_home)
    lateinit var mTabHomeLayout: LinearLayout

    @BindView(R.id.transaction_history)
    lateinit var mTabTransactionLayout: LinearLayout

    @BindView(R.id.lay_tab_rent)
    lateinit var mTabCardLayout: LinearLayout

    @BindView(R.id.lay_tab_help)
    lateinit var mTabHelpLayout: LinearLayout

    @BindView(R.id.lay_tab_more)
    lateinit var mTabMoreLayout: LinearLayout

    //--------------------------------------------------------------------------------------------------
    lateinit var mImgHomeDot: AppCompatImageView
//    var mImgHomeDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_home)

    lateinit var mImgTransactionDot: AppCompatImageView
//    var mImgTransactionDot: AppCompatImageView? = findViewById(R.id.img_transaction_dot_history)

    lateinit var mImgCardDot: AppCompatImageView
//    var mImgCardDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_rent)

    lateinit var mImgHelpDot: AppCompatImageView
//    var mImgHelpDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_help)

    lateinit var mImgMoreDot: AppCompatImageView
//    var mImgMoreDot: AppCompatImageView? = findViewById(R.id.img_tab_dot_more)

    //--------------------------------------------------------------------------------------------------
    @BindView(R.id.img_tab_home)
    lateinit var mImgHome: AppCompatImageView

    @BindView(R.id.img_transaction_history)
    lateinit var mImgTransactionHistory: AppCompatImageView

    @BindView(R.id.img_tab_rent)
    lateinit var mImgRent: AppCompatImageView

    @BindView(R.id.img_tab_help)
    lateinit var mImgHelp: AppCompatImageView

    @BindView(R.id.img_tab_more)
    lateinit var mImgMore: AppCompatImageView

    //--------------------------------------------------------------------------------------------------
    @BindView(R.id.txt_tab_home)
    lateinit var mHomeTextView: AppCompatTextView

    @BindView(R.id.txt_transaction_history)
    lateinit var mTransactionTextView: AppCompatTextView

    @BindView(R.id.txt_tab_search)
    lateinit var mCardTextView: AppCompatTextView

    @BindView(R.id.txt_tab_help)
    lateinit var mHelpTextView: AppCompatTextView

    @BindView(R.id.txt_tab_more)
    lateinit var mMoreTextView: AppCompatTextView
    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    override fun onItemClicked(rentItemDTO: RentItemDTO) {
        Toast.makeText(this,"Selected Item Serial No: ${rentItemDTO.serialNumber}",Toast.LENGTH_LONG).show()
        val intent = Intent(baseContext, ViewItemActivity::class.java)
        val gson = Gson()
        val jsonString = gson.toJson(rentItemDTO)
        intent.putExtra("item", jsonString)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_home)
        val binding: FragmentRentBinding = DataBindingUtil.setContentView(
            this, R.layout.fragment_rent
        )
        ButterKnife.bind(this)

        ///////// Temp Fix For android.os.NetworkOnMainThreadException at android.os.StrictMode$AndroidBlockGuardPolicy.onNetwork
        //Fix = https://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        mImgHomeDot = findViewById(R.id.img_tab_dot_home)
        mImgTransactionDot = findViewById(R.id.img_transaction_dot_history)
        mImgCardDot = findViewById(R.id.img_tab_dot_rent)
        mImgHelpDot = findViewById(R.id.img_tab_dot_help)
        mImgMoreDot = findViewById(R.id.img_tab_dot_more)

        changeBottomView(2)

        try {
            val profile: String =
                SharedPrefManager(this@RentActivity).getSharedPrefManager(this@RentActivity)!!
                    .getString(
                        "profile"
                    )

//            val profile = intent.getStringExtra("profileDetails")
            val gson = Gson()
            val profileDetails = gson.fromJson(profile, ProfileDTO::class.java)
            //Bind Profile Details
            binding.profile = profileDetails

            //Fetch Transaction History
            val responseDTO: Single<ResponseDTO> = rentARideService.getRentItems()
            val rentItemsList = responseDTO.blockingGet().data!!
            Log.i(TAG, "About to print: $rentItemsList")
            val json = gson.toJson(rentItemsList)
            val historyArr = gson.fromJson(json, Array<RentItemDTO>::class.java)
            val historyList = ArrayList<RentItemDTO>()
            historyArr.forEach {
                historyList.add(it)
            }
            //Pass to Layout
            if (historyList.size > 0) {
                loading_view.visibility = View.GONE
            }
            transact_grid_view.layoutManager = LinearLayoutManager(this)
            transact_grid_view.adapter = RentItemListAdapter(historyList, this)

            val transHist = findViewById<LinearLayout>(R.id.transaction_history)
            transHist.setOnClickListener {
                changeBottomView(1)
            }

        } catch (e: Exception) {
            Log.e(TAG, "Got Error:" + e.message)
            Log.wtf(TAG, e)
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
    private fun changeBottomView(index: Int) {
        Log.i(TAG, "Inside changeBottomView with::$index")
        when (index) {
            0 -> {
                mImgHomeDot.visibility = View.INVISIBLE
                mImgTransactionDot.visibility = View.INVISIBLE
                mImgCardDot.visibility = View.INVISIBLE
                mImgHelpDot.visibility = View.INVISIBLE
                mImgMoreDot.visibility = View.INVISIBLE
                mHomeTextView.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome.setImageResource(R.drawable.ic_home_selected)
                mImgTransactionHistory.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp.setImageResource(R.drawable.ic_help)
                mImgMore.setImageResource(R.drawable.ic_more)
                val intent = Intent(baseContext, HomeActivity::class.java)
                Log.d(TAG, "Navigate to Home")
                startActivity(intent)
            }
            1 -> {
                mImgHomeDot.visibility = View.INVISIBLE
                mImgTransactionDot.visibility = View.INVISIBLE
                mImgCardDot.visibility = View.INVISIBLE
                mImgHelpDot.visibility = View.INVISIBLE
                mImgMoreDot.visibility = View.INVISIBLE
                mHomeTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView!!.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mCardTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory.setImageResource(R.drawable.ic_baseline_history_24_selected)
                mImgRent.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp.setImageResource(R.drawable.ic_help)
                mImgMore.setImageResource(R.drawable.ic_more)
            }
            2 -> {
                mImgHomeDot.visibility = View.INVISIBLE
                mImgTransactionDot.visibility = View.INVISIBLE
                mImgCardDot.visibility = View.INVISIBLE
                mImgHelpDot.visibility = View.INVISIBLE
                mImgMoreDot.visibility = View.INVISIBLE
                mHomeTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mHelpTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent.setImageResource(R.drawable.ic_baseline_directions_bike_24_selected)
                mImgHelp.setImageResource(R.drawable.ic_help)
                mImgMore.setImageResource(R.drawable.ic_more)
            }
            3 -> {
                mImgHomeDot.visibility = View.INVISIBLE
                mImgTransactionDot.visibility = View.INVISIBLE
                mImgCardDot.visibility = View.INVISIBLE
                mImgHelpDot.visibility = View.INVISIBLE
                mImgMoreDot.visibility = View.INVISIBLE
                mHomeTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mMoreTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mImgHome.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp.setImageResource(R.drawable.ic_help_selected)
                mImgMore.setImageResource(R.drawable.ic_more)
            }
            4 -> {
                mImgHomeDot.visibility = View.INVISIBLE
                mImgTransactionDot.visibility = View.INVISIBLE
                mImgCardDot.visibility = View.INVISIBLE
                mImgHelpDot.visibility = View.INVISIBLE
                mImgMoreDot.visibility = View.INVISIBLE
                mHomeTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mTransactionTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mCardTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mHelpTextView.setTextColor(ContextCompat.getColor(this, R.color._8e8e93))
                mMoreTextView.setTextColor(ContextCompat.getColor(this, R.color._0082BB))
                mImgHome.setImageResource(R.drawable.ic_home_unselected)
                mImgTransactionHistory.setImageResource(R.drawable.ic_baseline_history_24)
                mImgRent.setImageResource(R.drawable.ic_baseline_directions_bike_24)
                mImgHelp.setImageResource(R.drawable.ic_help)
                mImgMore.setImageResource(R.drawable.ic_more_selected)
            }
        }
    }
    }