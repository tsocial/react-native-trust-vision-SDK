
import { NativeModules } from 'react-native';

const { RNTrustVisionRnsdkFramework } = NativeModules;

// Enum strings
export const TVConst = {
    Orientation: {
        LANDSCAPE: 'landscape',
        PORTRAIT: 'portrait'
    },
    QRType: {
        QRCODE: 'qrCode',
        BARCODE: 'barCode'
    },
    ActionMode: {
        FACE_MATCHING: 'FACE_MATCHING',
        FULL: 'FULL',
        LIVENESS: 'LIVENESS',
        EXTRACT_ID_INFO: 'READ_CARD_INFO'
    },
    LivenessMode: {
        ACTIVE: 'active',
        PASSIVE: 'passive',
        HYBRID: 'hybrid'
    },
    SelfieCameraMode: {
        FRONT: 'front',
        BACK: 'back',
        BOTH: 'both'
    },
    CompareImageResult: {
        MATCHED: 'matched',
        UNMATCHED: 'unmatched',
        UNSURE: 'unsure'
    },
    CardSide: {
        FRONT: 'front',
        BACK: 'back'
    }
}

export const TVErrorCode = {
    UNAUTHORIZED: 'authentication_missing_error',
    NETWORK_ERROR: 'network_error',
    INTERNAL_ERROR: 'internal_error',
    TIMEOUT_ERROR: 'timeout_error',
    CANCELATION_ERROR: 'sdk_canceled'
}

export default RNTrustVisionRnsdkFramework;
