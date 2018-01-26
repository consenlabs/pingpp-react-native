package com.pingplusplus.react;

import android.content.Intent;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.pingplusplus.android.PingppLog;
import com.pingplusplus.android.Pingpp;
import android.util.Log;
import com.facebook.react.bridge.ReadableArray;
import com.pingplusplus.ui.PingppUI;
import com.pingplusplus.ui.ChannelListener;
import com.pingplusplus.ui.PaymentHandler;
import org.json.JSONObject;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 2016/12/6
 * @time 下午9:41
 */

public class PingppModule extends ReactContextBaseJavaModule {

  public static Callback mResultCallback;

  public PingppModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override public String getName() {
    return "PingppModule";
  }
  
  @ReactMethod
  public void createPayment(String charge, Callback resultCallback) {
    mResultCallback = resultCallback;
    Intent intent = new Intent(getCurrentActivity(), PingppActivity.class);
    intent.putExtra("charge", charge);
    getCurrentActivity().startActivity(intent);
  }

  @ReactMethod
  public void getVersion(Callback callback) {
    callback.invoke(Pingpp.VERSION);
  }

  @ReactMethod
  public void showPaymentChannels(ReadableArray channels, final Callback callback) {
    String[] chs = (String[])channels.toArrayList().toArray(new String[channels.size()]);
    PingppUI.enableChannels(chs);
    PingppUI.showPaymentChannels(getCurrentActivity(), new ChannelListener(){
      public void selectChannel(String channel) {
        callback.invoke(channel);
      }
    });
  }

  @ReactMethod
  public void createPay(String charge, final Callback resultCallback) {
    PingppUI.createPay(getCurrentActivity(), charge, new PaymentHandler(){
      @Override public void handlePaymentResult(Intent data) {
        int code = data.getExtras().getInt("code");
        String result = data.getExtras().getString("result");
        try {
          JSONObject object = new JSONObject();
          if(code == 0) {
              object.put("pay_result", "cancel");
              object.put("error_msg", result);
          } else if(code == -1) {
              object.put("pay_result", "fail");
              object.put("error_msg", result);
          } else if(code == 1) {
              object.put("pay_result", "success");
          }
          resultCallback.invoke(object.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  @ReactMethod
  public void setDebug(boolean flag){
    PingppLog.DEBUG = flag;
  }
}
