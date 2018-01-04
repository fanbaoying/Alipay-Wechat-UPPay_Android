package com.ucmed.hn.xiangya.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ucmed.hn.xiangya.R;


import org.json.JSONObject;

import okhttp3.internal.Util;

/**
 * 作者：王海洋
 * 时间：2017/3/6 22:20
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_acticity);
        Log.i("charge next", "走了");
//
        api = WXAPIFactory.createWXAPI(this, "appid");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);

        Log.i("charge next", "走过了");
    }

    @Override
    public void onReq(BaseReq req) {

        Log.i("charge next", "也走了");

    }

    @Override
    public void onResp(BaseResp resp) {

        Log.i("charge resoult", String.valueOf(resp.errCode));
    }
}
