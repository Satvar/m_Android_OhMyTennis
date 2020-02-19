package com.tech.cloudnausor.ohmytennis.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethod
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.view.CardMultilineWidget
import com.tech.cloudnausor.ohmytennis.controller.ErrorDialogHandler
import com.tech.cloudnausor.ohmytennis.controller.ProgressDialogController
import com.tech.cloudnausor.ohmytennis.R
import com.tech.cloudnausor.ohmytennis.Settings
import com.tech.cloudnausor.ohmytennis.activity.individualcourse.IndividualCourseHomeActivity
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface
import com.tech.cloudnausor.ohmytennis.dto.SetPaymentDTO
import com.tech.cloudnausor.ohmytennis.response.PaymentResponseData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment_multiline.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PaymentMultilineActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val cardSources = ArrayList<Map<String, String>>()

    private lateinit var cardMultilineWidget: CardMultilineWidget
    private lateinit var progressDialogController: ProgressDialogController
    private lateinit var errorDialogHandler: ErrorDialogHandler
    private lateinit var simpleAdapter: SimpleAdapter
    internal var apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
    lateinit var map:Map<String,String>

    var booking_id:String = ""
    var amount:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_payment_multiline)

        PaymentConfiguration.init(this, Settings.PUBLISHABLE_KEY)

        cardMultilineWidget = findViewById(R.id.card_multiline_widget)
        progressDialogController = ProgressDialogController(supportFragmentManager, resources)

        errorDialogHandler = ErrorDialogHandler(this)

        booking_id = intent.getStringExtra("bookvalue")

//        apiInterface.bookingDetail(booking_id).enqueue(object : Callback<PaymentResponseData> {
//            override fun onResponse(call: Call<PaymentResponseData>, response: Response<PaymentResponseData>) {
//                assert(response.body() != null)
//                System.out.println("response.body()" + Gson().toJson(response.body()) )
//                if (response.body()?.getIsSuccess().toString() == "true") {
//
//                    amount = response.body()?.getData()?.getPaymentAvaibilityData()?.get(0)?.amount.toString()
////                    Toast.makeText(this@PaymentMultilineActivity, "Paiement réussi", Toast.LENGTH_LONG).show()
////                    val intent = Intent(this@PaymentMultilineActivity, IndividualCourseHomeActivity::class.java)
////                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                    startActivity(intent)
//                } else {
//
//                }
//            }
//
//            override fun onFailure(call: Call<PaymentResponseData>, t: Throwable) {
//                println("---> $call $t")
//            }
//        })

        val listView = findViewById<ListView>(R.id.card_list_pma)
        simpleAdapter = SimpleAdapter(
                this,
                cardSources,
                R.layout.list_item_layout,
                arrayOf("last4", "tokenId"),
                intArrayOf(R.id.last4, R.id.tokenId))

        listView.adapter = simpleAdapter
        compositeDisposable.add(RxView.clicks(findViewById(R.id.save_payment)).subscribe { saveCard() })

//        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)


        apiInterface.bookingDetail(booking_id).enqueue(object : Callback<PaymentResponseData> {
            override fun onResponse(call: Call<PaymentResponseData>, response: Response<PaymentResponseData>) {
                assert(response.body() != null)
                if (response.body()!!.getIsSuccess().equals("true")) {
                    if(response.body()!!.getData() != null){
                    if(response.body()!!.getData().paymentAvaibilityData != null){
                    val bookingData = response.body()!!.getData().paymentAvaibilityData[0]
                    var sss:String = "Payer "+bookingData.amount+" €"
                    save_payment.setText(sss)
                        amount = bookingData.amount
                    payment_form_title.setText("Booking ID : "+bookingData.booking_Id)
                    println("- --> " + Gson().toJson(response.body()))
                    }else{
                        Toast.makeText(this@PaymentMultilineActivity,"No booking data found", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PaymentMultilineActivity, IndividualCourseHomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    }else{
                        Toast.makeText(this@PaymentMultilineActivity, response.body()!!.getMessage(), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PaymentMultilineActivity, IndividualCourseHomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@PaymentMultilineActivity, response.body()!!.getMessage(), Toast.LENGTH_LONG).show()
                    val intent = Intent(this@PaymentMultilineActivity, IndividualCourseHomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<PaymentResponseData>, t: Throwable) {
                println("---> $call $t")
            }
        })

    }

    private fun saveCard() {
        val card = cardMultilineWidget.card ?: return

        val stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)
        val cardSourceParams = PaymentMethodCreateParams.create(card.toPaymentMethodParamsCard(), null)
        // Note: using this style of Observable creation results in us having a method that
        // will not be called until we subscribe to it.
        val tokenObservable = Observable.fromCallable { stripe.createPaymentMethodSynchronous(cardSourceParams) }

        compositeDisposable.add(tokenObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressDialogController.show(R.string.progressMessage) }
                .doOnComplete { progressDialogController.dismiss() }
                .subscribe(
                        { addToList(it)

                            apiInterface.setpayment("B",booking_id,amount,cardSources.get(cardSources.size-1).get("tokenId")).enqueue(object : Callback<SetPaymentDTO> {
                                override fun onResponse(call: Call<SetPaymentDTO>, response: Response<SetPaymentDTO>) {
                                    assert(response.body() != null)
                                    System.out.println("response.body()" + Gson().toJson(response.body()) )
                                    if (response.body()?.getIsSuccess().toString() == "true") {
                                        Toast.makeText(this@PaymentMultilineActivity, "Paiement réussi", Toast.LENGTH_LONG).show()
                                        val intent = Intent(this@PaymentMultilineActivity, IndividualCourseHomeActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(intent)
                                    } else {

                                    }
                                }

                                override fun onFailure(call: Call<SetPaymentDTO>, t: Throwable) {
                                    println("---> $call $t")
                                }
                            })
                        },
                        { throwable -> errorDialogHandler.show(throwable.localizedMessage.orEmpty()) }
                )
        )


    }

    private fun addToList(paymentMethod: PaymentMethod?) {
        if (paymentMethod?.card == null) {
            return
        }

        val paymentMethodCard = paymentMethod.card
        val endingIn = getString(R.string.endingIn)
          val map = mapOf(
                "last4" to endingIn + " " + paymentMethodCard!!.last4,
                "tokenId" to paymentMethod.id!!

        )
        cardSources.add(map)
        simpleAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}

