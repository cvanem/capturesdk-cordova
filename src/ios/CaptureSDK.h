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
    //NSString *onDataId = nil;    
}

- (void)viewDidLoad;
- (void)registerCallback:(CDVInvokedUrlCommand *)command;
- (void)registerErrorCallback:(CDVInvokedUrlCommand *)command;
- (void)registerArrivalCallback:(CDVInvokedUrlCommand *)command;
- (void)registerRemovalCallback:(CDVInvokedUrlCommand *)command;
- (void)testCallback:(CDVInvokedUrlCommand *)command;
- (void)getStatus:(CDVInvokedUrlCommand *)command;

@end
