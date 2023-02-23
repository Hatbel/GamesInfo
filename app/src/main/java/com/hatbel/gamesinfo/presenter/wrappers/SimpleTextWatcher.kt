package com.hatbel.gamesinfo.presenter.wrappers

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher: TextWatcher {
    override fun afterTextChanged(p0: Editable?) = Unit
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
}