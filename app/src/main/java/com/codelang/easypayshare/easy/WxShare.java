package com.codelang.easypayshare.easy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.codelang.easypayshare.wxapi.WXEntryActivity;
import com.codelang.easypayshare.wxapi.WXPayEntryActivity;

import java.lang.ref.WeakReference;


/**
 * @author wangqi
 * @since 2017/11/15 18:14
 */

public class WxShare extends ResultReceiver implements IWeixin {

    public static final String KEY_SHARE_PARAMS = "key_share_content";

    public static final String KEY_SHARE_LISTENER = "key_share_listener";

    public static final String KEY_TYPE = "key_type";

    public static final String KEY_PAY = "key_pay";


    private WeakReference<Context> contextWeakRef;
    private ShareCallBack callback;


    public WxShare(Context context) {
        super(null);
        contextWeakRef = new WeakReference<>(context);
    }


    @Override
    public void share(ShareParams params, ShareCallBack callback) {
        this.callback = callback;
        Context context = contextWeakRef.get();
        if (context != null) {
            context.startActivity(new Intent(context, WXEntryActivity.class)
                    .putExtra(KEY_TYPE, WXEntryActivity.KEY_SHARE)
                    .putExtra(KEY_SHARE_PARAMS, params)
                    .putExtra(KEY_SHARE_LISTENER, this)
            );
        }
    }

    @Override
    public void login(ShareCallBack callback) {
        this.callback = callback;
        Context context = contextWeakRef.get();
        if (context != null) {
            context.startActivity(new Intent(context, WXEntryActivity.class)
                    .putExtra(KEY_TYPE, WXEntryActivity.KEY_LOGIN)
                    .putExtra(KEY_SHARE_LISTENER, this)
            );
        }
    }

    @Override
    public void pay(WeixinPay payWeixin, ShareCallBack callback) {
        this.callback = callback;
        Context context = contextWeakRef.get();//
        if (context != null) {//
            context.startActivity(new Intent(context, WXPayEntryActivity.class)
                    .putExtra(KEY_SHARE_LISTENER, this)
                    .putExtra(KEY_PAY, payWeixin)
            );
        }
    }


    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        String result;
        if (resultData == null) {
            result = "null";
        } else {
            result = resultData.getString("result", "null");
        }
        if (resultCode == IWeixin.RESULT_SUCCESS) {
            if (callback != null) {
                callback.onSuccess(result);
            }
        } else if (resultCode == IWeixin.RESULT_CANCELD) {
            if (callback != null) {
                callback.onCanceled(result);
            }
        } else {
            if (callback != null) {
                callback.onFailed(result);
            }
        }
    }
}
