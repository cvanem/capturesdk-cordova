//
//  CaptureSDK.m
//  
//
//  Created by Jose Angarita on 5/17/16.
//
//

#import "CaptureSDK.h"

@implementation CaptureSDK

static NSString *dataCallbackId = nil;

- (void)registerCallback:(CDVInvokedUrlCommand *)command {
    //NSLog(@"Checking status");
    [self.commandDelegate runInBackground:^{
        /*
        NSString *portName = nil;
        NSString *emulation = nil;
        CDVPluginResult    *result = nil;
        StarPrinterStatus_2 status;
        SMPort *port = nil;
        if (command.arguments.count > 0) {
            portName = [command.arguments objectAtIndex:0];
            emulation = [command.arguments objectAtIndex:1];
        }
        NSString *portSettings = [self getPortSettingsOption:emulation];
        @try {
            
            port = [SMPort getPort:portName :portSettings :10000];     // 10000mS!!!
            
            // Sleep to avoid a problem which sometimes cannot communicate with Bluetooth.
     
            NSOperatingSystemVersion version = {11, 0, 0};
            BOOL isOSVer11OrLater = [[NSProcessInfo processInfo] isOperatingSystemAtLeastVersion:version];
            if ((isOSVer11OrLater) && ([portName.uppercaseString hasPrefix:@"BT:"])) {
                [NSThread sleepForTimeInterval:0.2];
            }
            
            [port getParsedStatus:&status :2];
            NSDictionary *firmwareInformation = [[NSMutableDictionary alloc] init];
            @try {
                firmwareInformation = [port getFirmwareInformation];
            }
            @catch (PortException *exception) {
                //unable to get Firmware
            }
          
            NSDictionary *statusDictionary = [self portStatusToDictionary:status :firmwareInformation];
            result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:statusDictionary];
        }
        @catch (PortException *exception) {
            NSLog(@"Port exception");
            result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[exception reason]];
        }
        @finally {
            if (port != nil) {
                [SMPort releasePort:port];
            }
        }
        */
        
        //NSLog(@"Sending status result");
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

-(void)testCallback:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
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
