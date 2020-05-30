package com.jzbn.huzhu1230.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.widget.base.TopBaseDialog;
import com.jzbn.huzhu1230.R;
import com.jzbn.huzhu1230.bean.TopMsgBean;


/**
 * Created by hecuncun on 2019/12/17
 */
public class TopMsgDialog extends TopBaseDialog<TopMsgDialog> {
    private TopMsgBean mBean;
    private ImageView mIvClose;

    public TopMsgDialog(Context context, TopMsgBean bean) {
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
       // tvTitle.setText(mBean.getTitle());
       // tvContent.setText(mBean.getTitle());
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
