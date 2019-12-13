package com.pingplusplus.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 2016/12/6
 * @time 下午9:46
 */

public class PingppPackage implements ReactPackage {

  @Override public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    return Arrays.asList(new NativeModule[]{
        // Modules from third-party
        new PingppModule(reactContext)
    });
  }

  // ReactNative v0.47 已移除该方法
  public List<Class<? extends JavaScriptModule>> createJSModules() {
    return Collections.emptyList();
  }

  @Override public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

}
