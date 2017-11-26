package com.codelang.easypayshare;

import android.app.Application;

import com.codelang.easypayshare.easy.EasyPayShare;

/**
 * @author wangqi
 * @since 2017/11/26 13:31
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EasyPayShare.getInstance().registWeixin(this, EasyPayShare.WEIXIN_APP_ID);
    }
}
