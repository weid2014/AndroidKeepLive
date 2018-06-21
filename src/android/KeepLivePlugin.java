package com.cesc.ewater.cordovaPlugin;

import android.content.Intent;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by weid on 2018/6/4.
 * 保活进程服务
 */

public class KeepLivePlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, String rawArgs, CallbackContext callbackContext) throws JSONException {
        return super.execute(action, rawArgs, callbackContext);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        return super.execute(action, args, callbackContext);
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            //以下用原生代码打开一个Act1Activity（可以理解为让界面跳转到Act1Activity这个界面）
            //PS：原生很多地方都要获取当前Activity的实例对象（如果在Activity里就用this），在CordovaPlugin用的是cordova.getActivity()
            Toast.makeText(cordova.getActivity(),"start",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(cordova.getActivity(), DaemonService.class);
            //传入参数，参数分别是key和value
            cordova.getActivity().startService(intent);
            return true;
        }
        return false;
    }
}
