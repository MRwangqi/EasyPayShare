package com.codelang.easypayshare.easy;

import android.app.Activity;
import android.os.Looper;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author wangqi
 * @since 2017/11/18 18:17
 */

public class AliShare implements IAliPay {

    private WeakReference<Activity> contextWeakRef;

    public AliShare(Activity activity) {
        contextWeakRef = new WeakReference<>(activity);
    }

    @Override
    public void share(ShareParams params, ShareCallBack callback) {

    }

    @Override
    public void login(ShareCallBack callback) {

    }

    @Override
    public void pay(String orderInfo, ShareCallBack callback) {
        ThreadPoolManager.getInstance().execute(new MyRunnable(orderInfo, callback));
    }


    private class MyRunnable implements Runnable {
        String orderInfo;
        ShareCallBack callback;

        public MyRunnable(String orderInfo, ShareCallBack callback) {
            this.orderInfo = orderInfo;
            this.callback = callback;
        }

        @Override
        public void run() {
            PayTask alipay = new PayTask(contextWeakRef.get());
            Map<String, String> result = alipay.payV2(orderInfo, true);
            AliPayResult payResult = new AliPayResult(result);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            Looper.prepare();
            if (TextUtils.equals(resultStatus, "9000")) {
                callback.onSuccess(resultInfo);
            } else {
                callback.onFailed("支付失败");
            }
            Looper.loop();
        }
    }
}
