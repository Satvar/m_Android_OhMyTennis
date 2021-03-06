package com.tech.cloudnausor.ohmytennis.module

import android.content.Context
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.view.CardInputWidget
import com.tech.cloudnausor.ohmytennis.controller.AsyncTaskTokenController
import com.tech.cloudnausor.ohmytennis.controller.ErrorDialogHandler
import com.tech.cloudnausor.ohmytennis.controller.ListViewController
import com.tech.cloudnausor.ohmytennis.controller.ProgressDialogController
import com.tech.cloudnausor.ohmytennis.controller.RxTokenController

/**
 * A dagger-free simple way to handle dependencies in the Example project. Most of this work would
 * ordinarily be done in a module class.
 */
class DependencyHandler(
    activity: AppCompatActivity,
    private val cardInputWidget: CardInputWidget,
    outputListView: ListView,
    private val publishableKey: String
) {

    private var asyncTaskController: AsyncTaskTokenController? = null
    private val context: Context = activity.applicationContext
    private val progressDialogController: ProgressDialogController = ProgressDialogController(
        activity.supportFragmentManager,
        activity.resources
    )
    private val errorDialogHandler: ErrorDialogHandler = ErrorDialogHandler(activity)
    private val listViewController: ListViewController = ListViewController(outputListView)
    private var rxTokenController: RxTokenController? = null

    /**
     * Attach a listener that creates a token using the [android.os.AsyncTask]-based method.
     * Only gets attached once, unless you call [.clearReferences].
     *
     * @param button a button that, when clicked, gets a token.
     */
    fun attachAsyncTaskTokenController(button: Button) {
        if (asyncTaskController == null) {
            asyncTaskController = AsyncTaskTokenController(
                button,
                cardInputWidget,
                context,
                errorDialogHandler,
                listViewController,
                progressDialogController,
                publishableKey
            )
        }
    }

    /**
     * Attach a listener that creates a token using a [rx.Subscription] and the
     * synchronous [com.stripe.android.Stripe.createCardTokenSynchronous] method.
     *
     * Only gets attached once, unless you call [.clearReferences].
     *
     * @param button a button that, when clicked, gets a token.
     */
    fun attachRxTokenController(button: Button) {
        if (rxTokenController == null) {
            rxTokenController = RxTokenController(
                button,
                cardInputWidget,
                context,
                listViewController,
                progressDialogController,
                publishableKey
            )
        }
    }

    /**
     * Clear all the references so that we can start over again.
     */
    fun clearReferences() {
        asyncTaskController?.detach()
        rxTokenController?.detach()

        asyncTaskController = null
        rxTokenController = null
    }
}
