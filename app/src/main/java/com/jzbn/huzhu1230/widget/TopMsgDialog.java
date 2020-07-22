package com.jzbn.huzhu1230.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.widget.base.TopBaseDialog;
import com.jzbn.huzhu1230.R;
import com.jzbn.huzhu1230.event.MsgNoticeEvent;


/**
 * Created by hecuncun on 2019/12/17
 */
public class TopMsgDialog extends TopBaseDialog<TopMsgDialog> {
    private MsgNoticeEvent mBean;
    private ImageView mIvClose;

    public TopMsgDialog(Context context, MsgNoticeEvent bean) {
        super(context);
        mBean=bean;
    }

    @Override
    public View onCreateView() {
        widthScale(0.9f);
        showAnim(new FlipVerticalSwingEnter());
        dismissAnim(null);
        View inflate = View.inflate(mContext, R.layout.dialog_top_msg, null);

        mIvClose = inflate.findViewById(R.id.iv_close);
        TextView tvPhone = inflate.findViewById(R.id.tv_phone);
        TextView tvGpsAddress = inflate.findViewById(R.id.tv_gps_address);
        TextView tvContent = inflate.findViewById(R.id.tv_content);
        tvPhone.setText(mBean.getPhone());
        tvGpsAddress.setText(mBean.getGpsArea());
        tvContent.setText(mBean.getContent());
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
