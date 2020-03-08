
import { NativeModules } from 'react-native';

const { RNTrustVisionRnsdkFramework } = NativeModules;

// Enum strings
export const TVConst = {
    TVOrientation: {
        LANDSCAPE: 'landscape',
        PORTRAIT: 'portrait'
    },
    TVQRType: {
        QRCODE: 'qrCode',
        BARCODE: 'barCode'
    },
    TVActionMode: {
        FACE_MATCHING: 'faceMatching',
        FULL: 'full',
        LIVENESS: 'liveness',
        EXTRACT_ID_INFO: 'extractIdInfo'
    },
    TVLivenessMode: {
        ACTIVE: 'active',
        PASSIVE: 'passive'
    },
    TVSelfieCameraMode: {
        FRONT: 'front',
        BACK: 'back',
        BOTH: 'both'
    },
    TVCompareImageResult: {
        MATCHED: 'matched',
        UNMATCHED: 'unmatched',
        UNSURE: 'unsure'
    }
}

export const TVErrorCode = {
    UNAUTHORIZED: 'authentication_missing_error',
    NETWORK_ERROR: 'network_error',
    INTERNAL_ERROR: 'internal_error',
    TIMEOUT_ERROR: 'internal_error'
}

export default RNTrustVisionRnsdkFramework;
