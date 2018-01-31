/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';

var NetUtil = require('./netutil.js');
var Pingpp = require('pingpp-react-native');

export default class PingppDemoApp extends Component {
  setDebugModel(enabled) {
    Pingpp.setDebugModel(enabled);
  };
  getVersion() {
    Pingpp.getVersion(function(res) {
      alert(res);
    });
  };
  pay(channel) {
    NetUtil.postJson(
      "http://218.244.151.190/demo/charge",
      {amount: 1, channel: channel, order_no: new Date().getTime()},
      function(object) {
        //调用 标准版 SDK 
        Pingpp.createPayment(
          {
            "object":object,
            "scheme":"pingppdemoapp"
          }, function(res, error) {
            alert(res);
            console.log(error);
            alert(JSON.stringify(error));
          }
        );
        //调用 UI版 SDK 
        // Pingpp.createPay(
        //   {
        //     "object":object,
        //     "scheme":"pingppdemoapp"
        //   }, function(res, error) {
        //     alert(res);
        //     console.log(error);
        //     alert(JSON.stringify(error));
        //   }
        // );

      }
    );
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress = {()=>{this.pay("alipay")}}>
          <Text style={styles.welcome}>支付宝</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={()=>{this.pay("wx")}}>
          <Text style={styles.welcome}>微信</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={()=>{this.pay("upacp")}}>
          <Text style={styles.welcome}>银联</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={()=>{this.pay("qpay")}}>
          <Text style={styles.welcome}>QQ钱包</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={()=>{this.setDebugModel(true)}}>
          <Text style={styles.welcome}>开启调试</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={()=>{this.getVersion()}}>
          <Text style={styles.welcome}>获取 SDK 版本号</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('PingppDemoApp', () => PingppDemoApp);
