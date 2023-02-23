package com.hatbel.gamesinfo.presenter.wrappers

import android.content.Context
import androidx.fragment.app.Fragment
import com.hatbel.gamesinfo.presenter.activity.HandleLoader

abstract class BaseFragment: Fragment(), HandleLoader {

    private lateinit var handleLoader: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        handleLoader = context
    }

    override fun handle(isShow: Boolean) {
        (handleLoader as? HandleLoader)?.handle(isShow)
    }
}