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

#pragma mark - SKTCaptureHelper delegate
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
        //NSString *text = [[decodedData.stringFromDecodedData componentsSeparatedByCharactersInSet:[NSCharacterSet newlineCharacterSet]] componentsJoinedByString:@""];
        /*self.decodedDataText.text =
        [self.decodedDataText.text stringByAppendingString: text];
        self.decodedDataText.text =
        [self.decodedDataText.text stringByAppendingString: @"\r\n"];
        */
    });
}

- (void)registerCallback:(CDVInvokedUrlCommand *)command {
    NSLog(@"Setting up Capture SDK");
    _capture = [SKTCaptureHelper sharedInstance];
    [_capture pushDelegate:self];

    SKTAppInfo* appInfo = [SKTAppInfo new];
    appInfo.AppID = @"ios:app.greenlink.cordova";
    appInfo.DeveloperID = @"43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8";
    appInfo.AppKey = @"MCwCFEbasc6tNWT3Z6tJAXgvTJvlhYp0AhQEJE6LFqI9CchefXg9CAJVfydjnQ==";
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
        [self.commandDelegate sendPluginResult:result callbackId:dataCallbackId];        
    }];
}

-(void)testCallback:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Successfully tested callback!"];        
        //dataCallbackId = command.callbackId;
        [result setKeepCallbackAsBool:YES]; 
        //[self.commandDelegate sendPluginResult:result callbackId:dataCallbackId];
        /*
        NSStringEncoding encoding = NSWindowsCP1252StringEncoding;
        NSString *portName = nil;
        NSString *emulation = nil;
        NSDictionary *printObj = nil;
        
  
        if (command.arguments.count > 0) {
            portName = [command.arguments objectAtIndex:0];
            emulation = [command.arguments objectAtIndex:1];
            printObj = [command.arguments objectAtIndex:2];
        };
        
        NSString *portSettings = [self getPortSettingsOption:emulation];
        NSString *text = [printObj valueForKey:@"text"];
        BOOL cutReceipt = ([printObj valueForKey:@"cutReceipt"]) ? YES : NO;
        BOOL openCashDrawer = ([printObj valueForKey:@"openCashDrawer"]) ? YES : NO;
        StarIoExtEmulation Emulation = [self getEmulation:emulation];
        
        
        ISCBBuilder *builder = [StarIoExt createCommandBuilder:Emulation];
        
        [builder beginDocument];
        
        [builder appendData:[text dataUsingEncoding:encoding]];
        
        if(cutReceipt == YES){
            [builder appendCutPaper:SCBCutPaperActionPartialCutWithFeed];
        }
        
        if(openCashDrawer == YES){
            [builder appendPeripheral:SCBPeripheralChannelNo1];
            [builder appendPeripheral:SCBPeripheralChannelNo2];
        }
        
        [builder endDocument];

        if(portName != nil && portName != (id)[NSNull null]){
            
                [self sendCommand:[builder.commands copy]
                         portName:portName
                     portSettings:portSettings
                          timeout:10000
                       callbackId:command.callbackId];
            
            }else{ //Use StarIOExtManager and send command to connected printer
                
            [self sendCommand:[builder.commands copy]
                   callbackId:command.callbackId];
                
        }
        */
    }];
}
@end
