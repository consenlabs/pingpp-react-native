package com.pingplusplus.react;

import android.content.Intent;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.pingplusplus.android.Pingpp;

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
  public void setDebug(boolean flag){
    Pingpp.DEBUG = flag;
  }
}
