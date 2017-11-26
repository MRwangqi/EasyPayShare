package com.codelang.easypayshare.easy;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * @author wangqi
 * @since 2017/11/15 10:21
 */

public class EasyPayShare {

    public static final String ZFB_APPID = "支付宝APP_ID";
    public static final String WEIXIN_APP_ID = "微信APP_ID";
    public static final String WEIXIN_SECRET = "微信SECRET";


    private static IWXAPI wxApi;


    private EasyPayShare() {
    }

    private static class InnerClass {
        static EasyPayShare Instance = new EasyPayShare();
    }

    public static EasyPayShare getInstance() {
        return InnerClass.Instance;
    }

    public void registWeixin(Context context, @NonNull String appId) {
        wxApi = WXAPIFactory.createWXAPI(context, appId, true);
        wxApi.registerApp(appId);
    }


    public IWXAPI getWxApi() {
        return wxApi;
    }


    public void doShareWx(Context context, ShareParams wxShareParams, ShareCallBack callBack) {
        IWeixin share = new WxShare(context);
        share.share(wxShareParams, callBack);
    }


    public void doLoginWx(Context context, ShareCallBack callBack) {
        IWeixin share = new WxShare(context);
        share.login(callBack);
    }

    /**
     * @param context   上下文
     * @param payWeixin 预支付订单
     * @param callBack  回调的引用
     */
    public void doPayWx(Context context, WeixinPay payWeixin, ShareCallBack callBack) {
        IWeixin share = new WxShare(context);
        share.pay(payWeixin, callBack);
    }


    public void doPayAli(String orderInfo, Activity activity, ShareCallBack callBack) {
        IAliPay aliPay = new AliShare(activity);
        aliPay.pay(orderInfo, callBack);
    }


    OnBackListener backListener;

    public void setOnBackListener(OnBackListener backListener) {
        this.backListener = backListener;
    }

    public interface OnBackListener {
        void back(AuthResult authResult);
    }


}
