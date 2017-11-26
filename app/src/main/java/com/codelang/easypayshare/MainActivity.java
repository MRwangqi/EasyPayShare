package com.codelang.easypayshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codelang.easypayshare.easy.EasyPayShare;
import com.codelang.easypayshare.easy.IWeixin;
import com.codelang.easypayshare.easy.ShareCallBack;
import com.codelang.easypayshare.easy.ShareParams;
import com.codelang.easypayshare.easy.WeixinPay;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 微信支付
     *
     * @param v
     */
    public void payWeixin(View v) {
        WeixinPay weixinPay = new WeixinPay();
        weixinPay.setAppid("");
        weixinPay.setOrdercode("");
        weixinPay.setNoncestr("");
        weixinPay.setPackag("");
        weixinPay.setPrepayid("");
        weixinPay.setPartnerid("");
        weixinPay.setSign("");
        weixinPay.setTimestamp("");
        EasyPayShare.getInstance().doPayWx(this, weixinPay, new ShareCallBack() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(String result) {
                Toast.makeText(MainActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String result) {
                Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 支付宝支付
     *
     * @param v
     */
    public void payAli(View v) {
        String orderInfo = "该订单信息需要从后台签名后获取";
        EasyPayShare.getInstance().doPayAli(orderInfo, this, new ShareCallBack() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(String result) {
                Toast.makeText(MainActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String result) {
                Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 微信登录
     * @param v
     */
    public void loginWx(View v){
        EasyPayShare.getInstance().doLoginWx(this, new ShareCallBack() {
            @Override
            public void onSuccess(String result) {
                /**
                 * result为用户登陆后获取的信息 是json字符串
                 */
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(String result) {
                Toast.makeText(MainActivity.this, "登录取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String result) {
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 微信分享
     * @param v
     */
    public void shareWx(View v ){
        ShareParams params=new ShareParams.Build()
                .withTitle("分享的标题")
                .withDescribe("分享的描述")
                .withUrl("分享的链接")
                .withShareType(IWeixin.WXSceneSession)
                .build();
        EasyPayShare.getInstance().doShareWx(this, params, new ShareCallBack() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(String result) {
                Toast.makeText(MainActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String result) {
                Toast.makeText(MainActivity.this, "分享取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
