package com.jzbn.huzhu1230.ui.publish

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.dialog_lose_reason_layout.*

class LoseReasonDialog : DialogFragment() {

    private var mOnDialogBtnClickListener: OnDialogBtnClickListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  Dialog(requireActivity(), R.style.BottomDialog);

        return dialog
    }

    override fun onResume() {
        super.onResume()
        dialog.window?.apply {
            val attributes = attributes
            attributes.gravity = Gravity.BOTTOM
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            setAttributes(attributes)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_lose_reason_layout,
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
        btn_cancel.setOnClickListener {
            dismiss()
        }

        reason_one.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(0)
            dismiss()
        }
        reason_two.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(1)
            dismiss()

        }
        reason_three.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(2)
            dismiss()

        }
        reason_four.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(3)
            dismiss()
        }

    }

    interface OnDialogBtnClickListener {
        fun onClickReasonPosition(pos: Int)
    }
}