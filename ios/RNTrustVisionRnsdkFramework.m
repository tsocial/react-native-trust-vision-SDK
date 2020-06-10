
#import <objc/runtime.h>
#import "RNTrustVisionRnsdkFramework.h"
#import <React/RCTConvert.h>
@import TrustVisionSDK;

@implementation RNTrustVisionRnsdkFramework

RCT_EXPORT_MODULE();

// MARK: - Flows
RCT_EXPORT_METHOD(startIdCapturing:(NSDictionary *)configDict
                  withResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    TVIdCardConfiguration *config = [TVIdCardConfiguration dictToObjWithDict: configDict];

    dispatch_async(dispatch_get_main_queue(), ^{
        UIViewController *mainVc = [TrustVisionSdk startIdCapturingWithConfiguration:config
                                                                             success:^(TVDetectionResult * result) {
            resolve([result toDictionary]);
        }
                                                                             failure:^(TVError * error) {
            [self rejectWithRejecter:reject TvError: error];
        }];
        
        [self presentViewController:mainVc];
    });
}

RCT_EXPORT_METHOD(startSelfieCapturing:(NSDictionary *)configDict
                  withResolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    TVSelfieConfiguration *config = [TVSelfieConfiguration dictToObjWithDict: configDict];

    dispatch_async(dispatch_get_main_queue(), ^{

        UIViewController *mainVc = [TrustVisionSdk startSelfieCapturingWithConfiguration:config
                                                                                 success:^(TVDetectionResult * result) {
            resolve([result toDictionary]);
        }
                                                                                 failure:^(TVError * error) {
            [self rejectWithRejecter:reject TvError: error];
        }];

        [self presentViewController:mainVc];
    });
}

// MARK: - Helpers
- (void) rejectWithCancelationErrorWithRejecter: (RCTPromiseRejectBlock)reject {
    reject(@"sdk_canceled", @"sdk is canceled by user", NULL);
}

- (void) presentViewController: (UIViewController *)vc {
    [[[[[UIApplication sharedApplication] delegate] window] rootViewController] presentViewController:vc animated:NO completion:nil];
}
         
- (void) rejectWithRejecter: (RCTPromiseRejectBlock)reject TvError: (TVError *)tvError {
    NSError *e = [NSError errorWithDomain:@"TrustVisionSDKError" code:[[tvError errorCode] integerValue] userInfo: nil];
    reject([tvError errorCode], [tvError description], e);
}

- (NSString *) getBase64StringFromImage: (UIImage *)image {
    NSData *imageData = UIImagePNGRepresentation(image);
    NSString *imageBase64 = [imageData base64EncodedStringWithOptions:NSDataBase64EncodingEndLineWithLineFeed];
    return imageBase64;
}

- (NSDictionary *)toDictionary: (NSObject *)obj {
    unsigned int count = 0;
    
    NSMutableDictionary *dictionary = [NSMutableDictionary new];
    objc_property_t *properties = class_copyPropertyList([obj class], &count);
    
    for (int i = 0; i < count; i++) {
        
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        id value = [obj valueForKey:key];
        
        if (value == nil) {
            // nothing todo
        }
        else if ([value isKindOfClass:[NSNumber class]]
                 || [value isKindOfClass:[NSString class]]
                 || [value isKindOfClass:[NSDictionary class]]
                 || [value isKindOfClass:[NSData class]]) {
            // TODO: extend to other types
            [dictionary setObject:value forKey:key];
        }
        else if ([value isKindOfClass:[UIImage class]]) {
            NSString *base64 = [self getBase64StringFromImage: value];
            [dictionary setObject:base64 forKey:key];
        }
        else if ([value isKindOfClass:[NSObject class]]) {
            [dictionary setObject:[self toDictionary: value] forKey:key];
        }
        else {
            NSLog(@"Invalid type for %@ (%@)", NSStringFromClass([obj class]), key);
        }
    }
    
    return dictionary;
}

@end
