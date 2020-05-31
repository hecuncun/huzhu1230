package com.jzbn.huzhu1230.ui.publish

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.dialog_release_layout.*

class PublishDialog : DialogFragment() {

    private var mOnDialogBtnClickListener: OnDialogBtnClickListener? = null

    override fun onResume() {
        super.onResume()
        val widthPixels = resources.displayMetrics.widthPixels
        dialog.window?.apply {
            setLayout((0.86f * widthPixels).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_release_layout,
            container, false)
    }

    fun setOnDialogClickListener(onDialogBtnClickListener: OnDialogBtnClickListener) {
            mOnDialogBtnClickListener = onDialogBtnClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_close.setOnClickListener {
            dismiss()
        }
        btn_release_emergency.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReleaseEmergency()
            dismiss()
        }
        btn_release_aed.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReleaseAed()
            dismiss()
        }

        btn_release_common.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReleaseCommon()
            dismiss()
        }
    }

    interface OnDialogBtnClickListener {
        fun onClickReleaseEmergency()
        fun onClickReleaseCommon()
        fun onClickReleaseAed()
    }
}