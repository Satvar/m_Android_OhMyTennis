package com.tech.cloudnausor.ohmytennis.activity

import android.app.Dialog
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.tech.cloudnausor.ohmytennis.R
import kotlinx.android.synthetic.main.activity_stripe.*
import java.util.*

class StripeActivity : AppCompatActivity() {

    lateinit var stripe: Stripe
    val publishKey: String = "pk_test_xxxxxxxxxxxxxxxxxxx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe)

        stripe = Stripe(this, publishKey)

        pay.setOnClickListener { OpenDialog() }
    }

    private fun OpenDialog() {

        var dialog = Dialog(this)
        dialog.setContentView(R.layout.stripe_payment_ui_card)
        var lp : WindowManager.LayoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        val submit = dialog.findViewById<View>(R.id.submit) as TextView
        val cardNo = dialog.findViewById<View>(R.id.cardNo) as EditText
        val month = dialog.findViewById<View>(R.id.month) as EditText
        val year = dialog.findViewById<View>(R.id.year) as EditText
        val cvv = dialog.findViewById<View>(R.id.cvv) as EditText

        submit.setOnClickListener {
            when {
                cardNo.length() == 0 || month.length() == 0 || year.length() == 0 || cvv.length() == 0 ->
                    Toast.makeText(this@StripeActivity, "Please fill all the fields"
                            , Toast.LENGTH_SHORT).show()
                cardNo.length() < 16 -> Toast.makeText(this@StripeActivity, "Please enter" +
                        " valid Card No.", Toast.LENGTH_SHORT).show()
                else -> {
                    validateCard(cardNo.text.toString(), month.text.toString(), year.text.toString(), cvv.text.toString())
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
        dialog.getWindow()?.setAttributes(lp)
    }

    private fun validateCard(card: String, month: String, year: String, cvv: String) {
        val card: Card.Builder = Card.Builder(card, Integer.valueOf(month), Integer.valueOf(year), cvv)


//        stripe.createToken(card, object :  ApiResultCallback<Token> {
//            override fun onSuccess(result: Token) {
//                Log.v("Token!","Token Created!!"+ result!!.id)
//                Toast.makeText(this@StripeActivity, "Token Created!!", Toast.LENGTH_SHORT).show()
//                chargeCard(result.id);
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onError(e: java.lang.Exception) {
//                Toast.makeText(this@StripeActivity, e!!.message, Toast.LENGTH_SHORT).show()
//                e.printStackTrace()
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
////            override fun onSuccess(token: Token?) {
////                Log.v("Token!","Token Created!!"+ token!!.getId())
////                Toast.makeText(this@StripeActivity, "Token Created!!", Toast.LENGTH_SHORT).show()
////                chargeCard(token.id);
////                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////            }
//
////            override fun onSuccess(token: MediaSession.Token?) {
////                Log.v("Token!","Token Created!!"+ token!!.getId())
////                Toast.makeText(this@StripeActivity, "Token Created!!", Toast.LENGTH_SHORT).show()
////                chargeCard(token.id);
////            }
////
////            override fun onError(error: Exception?) {
////                Toast.makeText(this@StripeActivity, error!!.message, Toast.LENGTH_SHORT).show()
////                error.printStackTrace()
////            }
//
//        })

    }


}


