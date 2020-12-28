//
// Created by Khanh on 11/01/2019.
//

#ifndef LivenessDetector_hpp
#define LivenessDetector_hpp
#define PLATFORM_UNKNOWN            0
#define PLATFORM_IOS                1
#define PLATFORM_ANDROID            2

// Determine tartet platform by compile environment macro.
#define TARGET_PLATFORM  PLATFORM_UNKNOWN

// iphone
#if ! TARGET_PLATFORM && (defined(TARGET_OS_IPHONE) || defined(TARGET_IPHONE_SIMULATOR))
#undef  TARGET_PLATFORM
#define TARGET_PLATFORM     PLATFORM_IOS
#endif

// android
#if ! TARGET_PLATFORM && defined(ANDROID)
#undef  TARGET_PLATFORM
#define TARGET_PLATFORM  PLATFORM_ANDROID
#endif

#ifdef __cplusplus
extern "C" {
#endif
    long initLib(int capacity);
    int detect(long ptr, int action);
    int addLandmarks(long ptr, float* x, float* y, int size);
    int* getSteps(long ptr);
#ifdef __cplusplus
}
#endif

#endif /* LivenessDetector_hpp */
