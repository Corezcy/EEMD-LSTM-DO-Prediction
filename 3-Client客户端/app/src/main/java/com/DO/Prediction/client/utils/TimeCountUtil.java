package com.DO.Prediction.client.utils;

import android.os.CountDownTimer;
import android.app.Activity;
import android.widget.TextView;
import com.DO.Prediction.client.R;


public class TimeCountUtil extends CountDownTimer {
    private Activity mActivity;
    private TextView btn;//按钮

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimeCountUtil( Activity mActivity,long millisInFuture, long countDownInterval,TextView btn) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.btn =btn;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setText(millisUntilFinished / 1000 + "秒后可重新发送");//设置倒计时时间

        //设置按钮为灰色，这时是不能点击的
        btn.setTextColor(mActivity.getResources().getColor(R.color.gray));
        //        Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
        //        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
        //        btn.setText(span);

    }

    @Override
    public void onFinish() {
        btn.setText("重新获取验证码");
        btn.setClickable(true);//重新获得点击
        btn.setTextColor(mActivity.getResources().getColor(R.color.coral));//还原背景色
    }
}
