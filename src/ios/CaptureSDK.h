//
//  CaptureSDK.h
//  
//
//  Created by Jose Angarita on 5/17/16.
//
//

#import <Cordova/CDVPlugin.h>
#import <Foundation/Foundation.h>
#import "SktCaptureHelper.h"
#import <Cordova/CDV.h>

@interface CaptureSDK : CDVPlugin <SKTCaptureHelperDelegate> {
    SKTCaptureHelper* _capture;
}

//@property (nonatomic) StarIoExtManager *printerManager;

- (void)viewDidLoad {
    [super viewDidLoad];

    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];

    /*SKTAppInfo* appInfo = [SKTAppInfo new];
    appInfo.DeveloperID =@"43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8";
    appInfo.AppKey = @"MC0CFA3t4MzdzMa97KWFTN8HEtGeTcYsAhUAk8EQbbFnSBgW2eocLcjiaUhjrY8=";
    appInfo.AppID = @"ios:com.socketmobile.MyTestApp";
    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];
    [_capture openWithAppInfo:appInfo completionHandler:^(SKTResult result) {
        NSLog(@"opening capture returns: %ld", result);
    }];
    */
    SKTAppInfo* appInfo = [SKTAppInfo new];
    appInfo.AppID = @"ios:capturesdk.cordova.CaptureSDK";
    appInfo.DeveloperID = @"43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8";
    appInfo.AppKey = @"MC4CFQDnEnESRw+quG5E+TdUcBclPTbVLgIVAPHgdQ1f9Yt7sS6beflEa5SEHF5r";
    SKTCaptureHelper* capture = [SKTCaptureHelper sharedInstance];
    [capture pushDelegate:self];
    [capture openWithAppInfo:appInfo completionHandler:^(SKTResult result) {
    NSLog(@"Opening Capture returns: %ld", result);
    }];
}

- (void)registerCallback:(CDVInvokedUrlCommand *)command;
- (void)testCallback:(CDVInvokedUrlCommand *)command;

@end
