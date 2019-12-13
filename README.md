# pingpp-react-native

Ping++ 是为移动端应用以及 PC 网页量身打造的下一代支付系统，通过一个 SDK 便可以同时支持移动端以及 PC 端网页的多种主流支付渠道，你只需要一次接入即可完成多个渠道的接入。 Ping++ SDK 包括 Client SDK 和 Server SDK 两部分，支持主流的七种后端开发语言，适配了 Android，iOS 和 HTML5 三种移动端平台以及 PC 端网页。

#### 支持以下渠道支付

1. 支付宝 (alipay)
2. 微信支付 (wx)
3. 银联支付(upacp)
4. QQ钱包 (qpay)
5. 招行一网通 (cmb_wallet)

### 安装

```sh
yarn add pingpp-react-native --save
cd ios && pod install && cd .. # CocoaPods on iOS needs this extra step
# run
yarn react-native run-ios
yarn react-native run-android
```

## Ping++ 标准版使用

### iOS端配置

#### 额外配置

1. iOS 9 以上版本如果需要使用支付宝和微信渠道，需要在 `Info.plist` 添加以下代码：

    ```
    <key>
        LSApplicationQueriesSchemes
    </key>
    <array>
        <string>weixin</string>
        <string>wechat</string>
        <string>alipay</string>
        <string>alipays</string>
        <string>mqq</string>
    </array>
    ```

2. 为了用户操作完成后能够跳转回你的应用，请务必添加 URL Schemes：在 Xcode 中，选择你的工程设置项，选中 TARGETS 一栏，在 Info 标签栏的 URL Types 添加 URL Schemes，如果使用微信，填入微信平台上注册的应用程序 id（为 wx 开头的字符串）。如果不使用微信，则自定义，建议起名稍复杂一些，尽量避免与其他程序冲突。允许英文字母和数字，首字母必须是英文字母，不允许特殊字符。

3. iOS 9 限制了 http 协议的访问，如果 App 需要访问 `http://`，需要在 `Info.plist` 添加如下代码：

    ```
    <key>
        NSAppTransportSecurity
    </key>
    <dict>
        <key>NSAllowsArbitraryLoads</key>
        <true/>
    </dict>
    ```

4. 如果使用到 `CmbWallet`（招行一网通） 需要把 招行一网通 提供的秘钥`CMBPublicKey` 添加到 `Info.plist`  如以下代码:

    ```
    <key>
        CMBPublicKey
    </key>          
    <string>
        IwxiAyJIT4tlwJSCbRRE0jZFTvYjt02/CrlutsMzd5O4B9PaVyUmIKSasdasdasdhWTyp3Bb9T7c9ujiUJOJ8y7893grwEae9yiOBoBmByVsCMTaxnc+lMr7A9ifk48Tz61WxsxnQTyYzrIVbuerQIUi3PSORwcPMRqi+XLX8qPXkNpLT9dMvjOasdasdasdUaAdPFc2YFHwl9dHf2ydQsxh1BHvaVO0OO+GtZ04ZKjxRyJW2HfghKLJijl;XTjrWSNizcdoefFKQsTdzvcPNvx7PsxuXKo9SosheeS/SHPk9sGNdwvL55yEBA8gNs0XZbkxJYjuwrwsQInC/N6QSaI0f0kyTA==
    </string>
    ```

5. `CmbWallet`（招行一网通）  需要把 `node_modules/pingpp-react-native/CmbWalletResources`目录下的 `SecreteKeyBoard`文件夹手动添加到 工程中的 `Assets.xcassets` 添加成功后即可删除该目录下的`SecreteKeyBoard`文件夹

### 使用方法

```jsx
var Pingpp = require('pingpp-react-native');
```

### iOS 使用示例

```jsx
/** 
* 调用支付
* @param object {"object":"charge 或 order" , "urlScheme":"YOU-URLSCHEME"}
* @param function completionCallback  支付结果回调 (result, error)
*/
Pingpp.createPayment({
    "object": obejct,
    "urlScheme": "YOU-URLSCHEME"
}, function(res, error) {
    console.log(res, error);
});

/**
* 开启/关闭 Ping++ debug 模式 
* @param boolean true 或 false
*/ 
Pingpp.setDebugModel(true);  

/**
* 获取 SDK 版本号 
* @param function completionCallback  结果回调 (version)
*/ 
Pingpp.getVersion(function(version) {
    alert(version);
});

```

####接收并处理交易结果
渠道为百度钱包或者渠道为支付宝但未安装支付宝钱包时，交易结果会在调起插件时的 Completion 中返回。渠道为微信、支付宝(安装了支付宝钱包)、银联或者测试模式时，请实现 UIApplicationDelegate 的 - application:openURL:xxxx: 方法:
打开 `AppDelegate.m`，添加一个函数来触发支付完成后的回调
```objective-c
//iOS 8 及以下
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    BOOL canHandleURL = [Pingpp handleOpenURL:url withCompletion:nil];
    return canHandleURL;
}
//iOS 9 及以上
- (BOOL)application:(UIApplication *)app
            openURL:(NSURL *)url
            options:(NSDictionary *)options {
    BOOL canHandleURL = [Pingpp handleOpenURL:url withCompletion:nil];
    return canHandleURL;
}
```

### Android 使用示例

##### 额外配置

- 招行一网通配置：
    需在string.xml中配置cmbkb_publickey字段, 如:

    ```xml
    <string name="cmbkb_publickey">填写自己的publickey</string>
    ```

#### 调用支付

```jsx
/**
 * 开启/关闭 Ping++ debug 模式 
 * @param boolean true 或 false
 */ 
Pingpp.setDebug(true);

/** 
 * 调用支付
 * @param charge 或 order (String 类型)
 * @param function completionCallback  支付结果回调 (result)
 */
Pingpp.createPayment(charge, function(result){
    //结果回调方法
    var res = JSON.parse(result);
    var pay_result = res.pay_result;
    var error_msg = res.error_msg;
    var extra_msg = res.extra_msg;
});
```

**关于如何使用 SDK 请参考 [开发者中心](https://www.pingxx.com/docs/index)**
