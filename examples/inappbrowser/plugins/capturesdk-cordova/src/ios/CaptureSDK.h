//
//  CaptureSDK.h
//  
//
//  Created by Jose Angarita on 5/17/16.
//
//

#import <Cordova/CDVPlugin.h>
#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import "Communication.h"

@interface CaptureSDK : CDVPlugin <StarIoExtManagerDelegate> {}

@property (nonatomic) StarIoExtManager *printerManager;

- (void)registerCallback:(CDVInvokedUrlCommand *)command;
- (void)testCallback:(CDVInvokedUrlCommand *)command;

@end
