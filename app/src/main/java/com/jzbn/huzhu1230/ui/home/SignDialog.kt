package com.jzbn.huzhu1230.ui.home

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.dialog_sign.*

// Created by hesanwei on 2020/5/31.
class SignDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity(), R.style.BottomDialog);

        return dialog
    }

    override fun onResume() {
        super.onResume()
        dialog.window?.apply {
            val attributes = attributes
            attributes.gravity = Gravity.CENTER
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            setAttributes(attributes)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { dismiss() }
    }

    companion object {
        fun newInstance(type: Int): SignDialog {
            return SignDialog()
        }
    }
}