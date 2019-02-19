//
//  CaptureSDK.m
//
//
//  Created by Chris Van Emmerik on 9/13/18.
//
//

#import "CaptureSDK.h"

@implementation CaptureSDK
static NSString *dataCallbackId = nil;
static NSString *errorCallbackId = nil;
static NSString *arrivalCallbackId = nil;
static NSString *removalCallbackId= nil;

#pragma mark - SKTCaptureHelper delegate


#pragma mark - Utilities
-(NSString*)getStatusFromDevices:(NSArray*)devices {
    NSString* status = @"";
    for(SKTCaptureHelperDevice* device in devices){
        status = [NSString stringWithFormat:@"%@ %@", status, device.friendlyName];
    }
    if(status.length == 0){
        status = @"No device connected";
    }
    //self.status.text = status;
    return status;
}

(void)getStatus:(CDVInvokedUrlCommand *)command {
    NSLog(@"Getting Capture SDK Status");    
    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];        
    SKTCaptureHelper* capture = [SKTCaptureHelper sharedInstance];
    [capture pushDelegate:self];        
    NSString *text = [self getStatusFromDevices:[_capture getDevicesList]];
        
    [self.commandDelegate runInBackground:^{        
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:text];                
        NSLog(@"Sending status: %s, callbackid: %ld", text, dataCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

/**
 * called when a error needs to be reported to the application
 *
 * @param error contains the error code
 * @param message contains an optional message, can be null
 */
-(void)didReceiveError:(SKTResult) error withMessage:(NSString*) message{
    NSLog(@"didReceiveError %ld with message: %@", error, message);    
    dispatch_async(dispatch_get_main_queue(), ^{        
        [self.commandDelegate runInBackground:^{
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
        [result setKeepCallbackAsBool:YES];        
        NSLog(@"Calling error callback.  Message: %s, callbackid: %ld", message, errorCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId: errorCallbackId];
        }];
    });
}

/**
 * called when a device has connected to the host
 *
 * @param device identifies the device that just connected
 * @param result contains an error if something went wrong during the device connection
 */
-(void)didNotifyArrivalForDevice:(SKTCaptureHelperDevice*) device withResult:(SKTResult) result{
    NSLog(@"didNotifyArrivalForDevice");            
    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];        
    SKTCaptureHelper* capture = [SKTCaptureHelper sharedInstance];
    [capture pushDelegate:self];        
    NSString *text = [self getStatusFromDevices:[_capture getDevicesList]];
    dispatch_async(dispatch_get_main_queue(), ^{        
        [self.commandDelegate runInBackground:^{
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:text];
        [result setKeepCallbackAsBool:YES];        
        NSLog(@"Calling arrival callback.  Status: %s, callbackid: %ld", text, arrivalCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId: arrivalCallbackId];
        }];
    });
}

/**
 * called when a device has disconnected from the host
 *
 * @param device identifies the device that has just disconnected
 * @param result contains an error if something went wrong during the device disconnection
 */
-(void)didNotifyRemovalForDevice:(SKTCaptureHelperDevice*) device withResult:(SKTResult) result{
    NSLog(@"didNotifyRemovalForDevice");    
    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];        
    SKTCaptureHelper* capture = [SKTCaptureHelper sharedInstance];
    [capture pushDelegate:self];        
    NSString *text = [self getStatusFromDevices:[_capture getDevicesList]];
    dispatch_async(dispatch_get_main_queue(), ^{        
        [self.commandDelegate runInBackground:^{        
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:text];
        [result setKeepCallbackAsBool:YES];        
        NSLog(@"Calling removal callback.  Status: %s, callbackid: %ld", text, removalCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:removalCallbackId];
        }];
    });
}

/**
 * called when decoded data are received from a device
 *
 * @param decodedData contains the decoded data
 * @param device identifies the device from which the decoded data comes from
 * @param result contains an error if something wrong happen while getting the decoded data
 * or if the SoftScan trigger operation has been cancelled
 */
-(void)didReceiveDecodedData:(SKTCaptureDecodedData*) decodedData fromDevice:(SKTCaptureHelperDevice*) device withResult:(SKTResult) result{
    NSLog(@"didReceiveDecodedData");
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *text = [[decodedData.stringFromDecodedData componentsSeparatedByCharactersInSet:[NSCharacterSet newlineCharacterSet]] componentsJoinedByString:@""];
        [self.commandDelegate runInBackground:^{
        NSLog(@"Receive Decoded Data callback");
        //CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Received Decoded Data!"];
        //CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:text];
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArrayBuffer:decodedData.DecodedData];
        //dataCallbackId = command.callbackId;
        [result setKeepCallbackAsBool:YES];
        NSLog(@"Sending decoded data result %s to callbackid: %ld", text, dataCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:dataCallbackId];
        }];

    });
}

- (void)registerCallback:(CDVInvokedUrlCommand *)command {
    NSLog(@"Setting up Capture SDK");
    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];

    SKTAppInfo* appInfo = [SKTAppInfo new];
    appInfo.AppID = @"ios:greenlink.web.app";
    appInfo.DeveloperID = @"43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8";
    appInfo.AppKey = @"MC4CFQD290HRj0qh7xekM0hYWhCuxyzelAIVAIgDtGLwZvYcf6IrALgsfJeTSq6b";
    SKTCaptureHelper* capture = [SKTCaptureHelper sharedInstance];
    [capture pushDelegate:self];
    [capture openWithAppInfo:appInfo completionHandler:^(SKTResult result) {
    NSLog(@"Opening Capture returns: %ld", result);
    }];

    //SKTCaptureHelper* capture = [SKTCaptureHelper sharedInstance];
    //[capture pushDelegate:self];
    //[capture openWithAppInfo:appInfo completionHandler:^(SKTResult result) {
    //NSLog(@"Opening Capture returns: %ld", result);
    //}];

    [self.commandDelegate runInBackground:^{
        NSLog(@"Registering callback");
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Successfully registered callback!"];
        dataCallbackId = command.callbackId;
        [result setKeepCallbackAsBool:YES];
        NSLog(@"Registered callback");
        NSLog(@"Sending register callback result: %ld", dataCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:dataCallbackId];
    }];
}

- (void)registerErrorCallback:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Successfully registered callback!"];
        dataCallbackId = command.callbackId;
        [result setKeepCallbackAsBool:YES];
        NSLog(@"Registered error callback");
        NSLog(@"Sending register callback result: %ld", errorCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:errorCallbackId];
    }];
}

- (void)registerRemovalCallback:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Successfully registered callback!"];
        dataCallbackId = command.callbackId;
        [result setKeepCallbackAsBool:YES];
        NSLog(@"Registered removal callback");
        NSLog(@"Sending register callback result: %ld", errorCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:errorCallbackId];
    }];
}

- (void)registerArrivalCallback:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Successfully registered callback!"];
        dataCallbackId = command.callbackId;
        [result setKeepCallbackAsBool:YES];
        NSLog(@"Registered arrival callback");
        NSLog(@"Sending register callback result: %ld", errorCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:errorCallbackId];
    }];
}

-(void)testCallback:(CDVInvokedUrlCommand *)command {
    NSLog(@"test Callback Executing");
    [self.commandDelegate runInBackground:^{
        NSLog(@"test Callback Preparing result");
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Successfully tested callback!"];
        [result setKeepCallbackAsBool:YES];
        NSLog(@"Sending test callback result: %ld", dataCallbackId );
        [self.commandDelegate sendPluginResult:result callbackId:dataCallbackId];
    }];
}
@end
