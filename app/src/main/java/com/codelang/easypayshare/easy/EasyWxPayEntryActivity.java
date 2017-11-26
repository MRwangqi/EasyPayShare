package com.codelang.easypayshare.easy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @author wangqi
 * @since 2017/11/18 15:25
 */

public class EasyWxPayEntryActivity extends Activity implements IWXAPIEventHandler {
    Bundle bundle;
    ResultReceiver listener;
    WeixinPay payWeixin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasyPayShare.getInstance().getWxApi().handleIntent(getIntent(), this);
        // 判断是否安装了微信客户端
        if (!EasyPayShare.getInstance().getWxApi().isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端！", Toast.LENGTH_SHORT).show();
            finish();
        }
        bundle = getIntent().getExtras();
        listener = bundle.getParcelable(WxShare.KEY_SHARE_LISTENER);
        payWeixin = (WeixinPay) bundle.getSerializable(WxShare.KEY_PAY);

        pay();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        EasyPayShare.getInstance().getWxApi().handleIntent(intent, this);
    }


    private void pay() {
        PayReq req = new PayReq();
        req.appId = payWeixin.getAppid();
        req.partnerId = payWeixin.getPartnerid();
        req.prepayId = payWeixin.getPrepayid();
        req.packageValue = payWeixin.getPackag();
        req.nonceStr = payWeixin.getNoncestr();
        req.timeStamp = payWeixin.getTimestamp();
        req.sign = payWeixin.getSign();
        EasyPayShare.getInstance().getWxApi().sendReq(req);
    }


    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                listener.send(IWeixin.RESULT_SUCCESS, bundle);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                listener.send(IWeixin.RESULT_CANCELD, bundle);
                break;
            default:
                listener.send(IWeixin.RESULT_FAIL, bundle);
                break;
        }
        finish();
    }
}