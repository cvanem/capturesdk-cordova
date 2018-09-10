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

@property (weak, nonatomic) IBOutlet UITextView *decodedDataText;

- (void)viewDidLoad;
- (void)registerCallback:(CDVInvokedUrlCommand *)command;
- (void)testCallback:(CDVInvokedUrlCommand *)command;

@end
