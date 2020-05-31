package com.jzbn.huzhu1230.ui.publish

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.dialog_lose_mode_layout.*

class LoseModeDialog : DialogFragment() {

    private var mOnDialogBtnClickListener: OnDialogBtnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity(), R.style.BottomDialog);

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
        return inflater.inflate(R.layout.dialog_lose_mode_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_close.setOnClickListener {
            dismiss()
        }
        btn_cancel.setOnClickListener {
            dismiss()
        }

        mode_one.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(0)
            dismiss()

        }
        mode_two.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(1)
            dismiss()
        }
        mode_three.setOnClickListener {
            mOnDialogBtnClickListener?.onClickReasonPosition(2)
            dismiss()
        }
    }

    fun setOnDialogClickListener(onDialogBtnClickListener: OnDialogBtnClickListener) {
        mOnDialogBtnClickListener = onDialogBtnClickListener
    }

    interface OnDialogBtnClickListener {
        fun onClickReasonPosition(pos: Int)
    }
}