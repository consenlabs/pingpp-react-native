//
//  PingppModule.m
//  PingppModule
//
//  Created by 于立涛 on 2017/3/7.
//  Copyright © 2017年 于立涛. All rights reserved.
//

#import "PingppModule.h"
#import "Pingpp.h"
#import "Pingpp+UI.h"
@implementation PingppModule
RCT_EXPORT_MODULE(PingppModule);

RCT_EXPORT_METHOD(createPayment:(NSDictionary *)paramDict
                  completionBlock:(RCTResponseSenderBlock)completionBlock)
{
    
    NSString *object = [paramDict objectForKey:@"object"];
    NSString *scheme = [paramDict objectForKey:@"urlScheme"];
    
    if (scheme == nil) {
        scheme = [self getUrlScheme];
    }
    UIViewController * viewController = [UIApplication sharedApplication].keyWindow.rootViewController;
    
    [Pingpp ignoreResultUrl:YES];
    dispatch_sync(dispatch_get_main_queue(), ^{
        [Pingpp createPay:object viewController:viewController appURLScheme:scheme withCompletion:^(NSString *result, PingppError *error) {
            if (error) {
                completionBlock(@[result,@{@"code":[NSNumber numberWithInteger:error.code],@"message":error.getMsg}]);
            }else{
                completionBlock(@[result]);
            }
        }];
    });
}

RCT_EXPORT_METHOD(createPay:(NSDictionary *)paramDict
                  completionBlock:(RCTResponseSenderBlock)completionBlock)
{
    
    NSString *object = [paramDict objectForKey:@"object"];
    NSString *scheme = [paramDict objectForKey:@"urlScheme"];
    
    if (scheme == nil) {
        scheme = [self getUrlScheme];
    }
    UIViewController * viewController = [UIApplication sharedApplication].keyWindow.rootViewController;
    
    [Pingpp ignoreResultUrl:YES];
    dispatch_sync(dispatch_get_main_queue(), ^{
        [Pingpp createPayment:object viewController:viewController appURLScheme:scheme withCompletion:^(NSString *result, PingppError *error) {
            if (error) {
                completionBlock(@[result,@{@"code":[NSNumber numberWithInteger:error.code],@"message":error.getMsg}]);
            }else{
                completionBlock(@[result]);
            }
        }];
    });
}


RCT_EXPORT_METHOD(setDebugModel:(BOOL)enabled){
    [Pingpp setDebugMode:enabled];
    
    if (enabled) {
        NSLog(@"成功开启 Ping++ DebugMode");
    }else{
        NSLog(@"成功关闭 Ping++ DebugMode");
    }
    
}

RCT_EXPORT_METHOD(getVersion:(RCTResponseSenderBlock)block){
    block(@[[Pingpp version]]);
}

- (NSString *)getUrlScheme {
    NSArray *urlSchemeList = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleURLTypes"];
    if ([urlSchemeList count] == 0) {
        return nil;
    }
    NSDictionary *urlSchemeType = [urlSchemeList objectAtIndex:0];
    NSArray *schemes = [urlSchemeType objectForKey:@"CFBundleURLSchemes"];
    if ([schemes count] == 0) {
        return nil;
    }
    return [schemes objectAtIndex:0];
}

@end
