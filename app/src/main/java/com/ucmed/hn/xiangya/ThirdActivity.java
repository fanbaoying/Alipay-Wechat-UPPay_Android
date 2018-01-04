package com.ucmed.hn.xiangya;

//com.ucmed.hn.xiangya

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.ucmed.hn.xiangya.bean.ChargeBean;
import com.ucmed.hn.xiangya.bean.ChargeWXBean;
import com.ucmed.hn.xiangya.bean.PayBean;
import com.ucmed.hn.xiangya.bean.PayResult;



/**
 * Created by fby on 2017/5/19.
 */

public class ThirdActivity extends Activity {
    private static final int SDK_PAY_FLAG = 1;//原生端调用支付宝
    private static final int SDK_PAY_WECHAT = 2;//原生端调用微信
    private static final int SDK_PAY_UPPAY = 3;//原生端调用银联
    private static final String CHARGE_URL = "自己的支付url";

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG) {

                Toast.makeText(ThirdActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();

                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
//                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();

                Log.i("message-------------------------------------", resultStatus);

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (resultStatus.equals("9000")) {
                    Toast.makeText(ThirdActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                }else if (resultStatus.equals("4000")){

                    // 4000为支付失败，包括用户主动取消支付，或者系统返回的错误
                    Toast.makeText(ThirdActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                }else if (resultStatus.equals("6001")){

                    // 6001为取消支付，或者系统返回的错误
                    Toast.makeText(ThirdActivity.this, "取消支付", Toast.LENGTH_SHORT).show();

                }else if (resultStatus.equals("8000")) {
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    Toast.makeText(ThirdActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                }else {

                    // 其他为系统返回的错误
                    Toast.makeText(ThirdActivity.this, "支付错误", Toast.LENGTH_SHORT).show();

                }


            }else if (msg.what == SDK_PAY_UPPAY){
                String tn = (String) msg.obj;
                Log.i("charge", tn);
                int ret = UPPayAssistEx.startPay (ThirdActivity.this, null, null, tn, "01");
                Log.i("charge", String.valueOf(ret));
            }else if (msg.what == SDK_PAY_WECHAT){
                ChargeWXBean charge =  (ChargeWXBean) msg.obj;
                String packsges = charge.getResult().getCredential().getPackages();
                String appid = charge.getResult().getCredential().getAppid();
                String partenerid = charge.getResult().getCredential().getPartnerid();
                String prepayid = charge.getResult().getCredential().getPrepayid();
                String noncestr = charge.getResult().getCredential().getNoncestr();
                String timestamp = charge.getResult().getCredential().getTimestamp();
                String sign = charge.getResult().getCredential().getSign();
                Log.i("charge packsges----", packsges);
                Log.i("charge appid----", appid);
                Log.i("charge partenerid----", partenerid);
                Log.i("charge prepayid----", prepayid);
                Log.i("charge noncestr----", noncestr);
                Log.i("charge timestamp----", timestamp);
                Log.i("charge sign----", sign);


                final IWXAPI msgApi = WXAPIFactory.createWXAPI(ThirdActivity.this, null);

                msgApi.registerApp("微信appid");

                PayReq req = new PayReq();
                req.appId = appid;
                req.partnerId = partenerid;
                req.prepayId = prepayid;
                req.nonceStr = noncestr;
                req.timeStamp = timestamp;
                req.packageValue = packsges;
                req.sign = sign;
                msgApi.sendReq(req);

            }
        }
    };

    public static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_acticity);

    }

    //    支付按钮
    public void onClick(View view) {


        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                String data = null;
                String channel = "ALI";

                Date now = new Date();
                String orderNo = getOrderNo(now);
                String newTime = getNewTime(now);
                Log.i("charge newTime", newTime);
                int amount = 1;

                PayBean payBean = new PayBean();

                payBean.setPayChannel(channel);
                payBean.setOrderTime(newTime);
                payBean.setMerchantNo("12345678");

                payBean.setMerchantOrderNo(orderNo);

                payBean.setProductName("支付测试-" + orderNo);
                payBean.setAmount(String.valueOf(amount));
                payBean.setReturnUrl("");
                payBean.setNotifyUrl("");
                payBean.setOrderPeriod("10");
                payBean.setDesc("11");
                payBean.setTrxType("");
                payBean.setPayType("APP");
                payBean.setFundIntoType("");

                String json = new Gson().toJson(payBean);
                Log.i("charge request", json);
                try {
                    data = postJson(CHARGE_URL, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("charge response", data);

                // 调起支付界面
//                AgreePay.getInstance().pay(ThirdActivity.this, data, "alipay");

                ChargeBean charge = new Gson().fromJson(data, ChargeBean.class);
                String credential = (String) charge.getResult().getCredential();
                Log.i("credential", credential);

                PayTask alipay = new PayTask(ThirdActivity.this);
                Map<String, String> result = alipay.payV2(credential, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }


    public void wxClick(View view) {


        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                Date now = new Date();
                String data = null;
                String channel = "WEIXIN";
                String orderNo = getOrderNo(now);
                int amount = 1;

                PayBean payBean = new PayBean();

                payBean.setPayChannel(channel);
                payBean.setOrderTime(getNewTime(now));
                payBean.setMerchantNo("12345678");

                payBean.setMerchantOrderNo(orderNo);

                payBean.setProductName("支付测试-" + orderNo);
                payBean.setAmount(String.valueOf(amount));
                payBean.setReturnUrl("");
                payBean.setNotifyUrl("");
                payBean.setOrderPeriod("10");
                payBean.setDesc("11");
                payBean.setTrxType("");
                payBean.setPayType("APP");
                payBean.setFundIntoType("");

                String json = new Gson().toJson(payBean);
                Log.i("charge request wechat", json);
                try {
                    data = postJson(CHARGE_URL, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("charge response wechat", data);

                ChargeWXBean charge = new Gson().fromJson(data.replace("package", "packages"), ChargeWXBean.class);


                Message msg = new Message();
                msg.what = SDK_PAY_WECHAT;
                msg.obj = charge;
                mHandler.sendMessage(msg);

            }
        };
// 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }

    public void uppayClick(View view) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                Date now = new Date();
                String data = null;
                String channel = "ACP";
                String orderNo = getOrderNo(now);
                String newTime = getNewTime(now);
                int amount = 1;

                PayBean payBean = new PayBean();

                payBean.setPayChannel(channel);
                payBean.setOrderTime(newTime);
                payBean.setMerchantNo("12345678");

                payBean.setMerchantOrderNo(orderNo);

                payBean.setProductName("支付测试-" + orderNo);
                payBean.setAmount(String.valueOf(amount));
                payBean.setReturnUrl("");
                payBean.setNotifyUrl("");
                payBean.setOrderPeriod("10");
                payBean.setDesc("11");
                payBean.setTrxType("");
                payBean.setPayType("APP");
                payBean.setFundIntoType("");

                String json = new Gson().toJson(payBean);
                Log.i("charge request uppay", json);
                try {
                    data = postJson(CHARGE_URL, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("charge response uppay", data);

                // 调起支付界面
//                AgreePay.getInstance().pay(ThirdActivity.this, data, "uppay");

                ChargeBean charge = new Gson().fromJson(data, ChargeBean.class);
                String tn = (String)charge.getResult().getCredential();
                Log.i("charge tn----", tn);


                Message msg = new Message();
                msg.what = SDK_PAY_UPPAY;
                msg.obj = tn;
                mHandler.sendMessage(msg);

                //TODO 银联支付

                /*****************************************************************
                 * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
                 *****************************************************************/
//                 String mMode = "01";
                /*************************************************
                 * 步骤2：通过银联工具类启动支付插件
                 ************************************************/
//                UPPayAssistEx.startPayByJAR(ThirdActivity.this,null, null, null,
//                        tn, "01");


            }
        };
// 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    //    回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
//                try {
//                    JSONObject resultJson = new JSONObject(result);
//                    String sign = resultJson.getString("sign");
//                    String dataOrg = resultJson.getString("data");
//                    // 验签证书同后台验签证书
//                    // 此处的verify，商户需送去商户后台做验签
//                    boolean ret = verify(dataOrg, sign, mMode);
//                    if (ret) {
//                        // 验证通过后，显示支付结果
//                        msg = "支付成功！";
//                    } else {
//                        // 验证不通过后的处理
//                        // 建议通过商户后台查询支付结果
                //               msg = "支付失败！";
//                    }
//                } catch (JSONException e) {
//                }
//            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                //               msg = "支付成功！";
//            }
                msg = "支付成功！";
            } else if (str.equalsIgnoreCase("fail")) {
                msg = "支付失败！";
            } else if (str.equalsIgnoreCase("cancel")) {
                msg = "用户取消了支付";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }


    private String getOrderNo(Date now) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(now);
    }

    private String getNewTime(Date now) {
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdfs.format(now);
    }





}

