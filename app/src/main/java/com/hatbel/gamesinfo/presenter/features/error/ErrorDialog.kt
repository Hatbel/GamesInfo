package com.hatbel.gamesinfo.presenter.features.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hatbel.gamesinfo.databinding.DialogErrorBinding

class ErrorDialog(
    private val title: String = "Error occurred",
    private val message: String = "",
    private val yesButtonText: String = "Ok",
) : DialogFragment() {

    private lateinit var binding: DialogErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding.apply {
            dialogErrorMessage.text = message
            dialogErrorTitle.text = title
            dialogErrorButton.text = yesButtonText
            dialogErrorButton.setOnClickListener {
                dismiss()
            }
        }
        dialog?.setCancelable(false)
    }
}