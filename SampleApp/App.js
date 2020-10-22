/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Button,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import RNTrustVisionRnsdkFramework, {
  TVConst,
  TVErrorCode,
} from 'react-native-trust-vision-SDK';

const App: () => React$Node = () => {
  const onPress = async () => {
    try {
      await RNTrustVisionRnsdkFramework.initialize(
        '5767c20d-87aa-4cad-8dbb-f5429f76c34b',
        'c1446919-e60a-4575-a05d-304318212a1b',
        null,
        true,
      );
      const cardTypes = await RNTrustVisionRnsdkFramework.getCardTypes();
      console.log('Card type list', cardTypes)

      // Full flow
      // const config = {
      //   cardType: cardTypes[0],
      //   cameraOption: TVConst.SelfieCameraMode.BACK,
      //   isEnableSound: true,
      //   isEnableSelfieSanityCheck: true,
      //   isEnableIDSanityCheck: true,
      //   livenessMode: TVConst.LivenessMode.PASSIVE,
      // };
      // console.log('Config', config);
      // const result = await RNTrustVisionRnsdkFramework.startFlow(config);

      // Selfie Capturing
      const config = {
        cameraOption: TVConst.SelfieCameraMode.BOTH,
        isEnableSound: true,
        isEnableSanityCheck: true,
        livenessMode: TVConst.LivenessMode.HYBRID,
      };
      console.log('Config', config);
      const result = await RNTrustVisionRnsdkFramework.startSelfieCapturing(config);

      // Id capturing
      // const config = {
      //   cardType: cardTypes[0],
      //   cardSide: TVConst.CardSide.BACK,
      //   isEnableSound: true,
      //   isEnableSanityCheck: true,
      //   isReadBothSide: true
      // };
      // console.log('Config', config);
      // const result = await RNTrustVisionRnsdkFramework.startIdCapturing(config);

      console.log('Result', result);

      if (result.idSanityResult && result.idSanityResult.error) {
        switch (result.idSanityResult.error) {
          case 'image_too_blur':
            // Image is too blurry
            break;
          case 'image_too_dark':
            // Image is too dark
            break;
          case 'image_too_bright':
            // Image is too bright
            break;
          case 'image_has_hole':
            // Image has holes
            break;
          case 'image_has_cut':
            // Images has been cut
            break;
          case 'image_has_hole_and_cut':
            // Images has holes and has been cut
            break;
        }
      }

      if (result.selfieSanityResult && result.selfieSanityResult.error) {
        switch (result.selfieSanityResult.error) {
          case 'image_too_blur':
            // Image is too blurry
            break;
          case 'image_too_dark':
            // Image is too dark
            break;
          case 'image_too_bright':
            // Image is too bright
            break;
          case 'not_white_background':
            // The background is not white enough
            break;
          case 'not_qualified':
            // The face is not qualified, could be occluded, covered or having something unusal
            break;
          case 'image_has_multiple_faces':
            // Image has multiple faces
            break;
          case 'image_has_no_faces':
            // Image does not have any face
            break;
          case 'right':
            // Face is looking to the right
            break;
          case 'left':
            // Face is looking to the left
            break;
          case 'open_eye,closed_eye':
            // Right eye is closed
            break;
          case 'closed_eye,open_eye':
            // Left eye is closed
            break;
          case 'open_eye,sunglasses':
            // Sunglass covers the right eye
            break;
          case 'sunglasses,open_eye':
            // Sunglass covers the left eye
            break;
          case 'closed_eye,closed_eye':
            // Both eyes are closed
            break;
          case 'closed_eye,sunglasses':
            // Left eye is closed, sunglass covers the right eye
            break;
          case 'sunglasses,closed_eye':
            // Sunglass covers the right eye, right eye is closed
            break;
          case 'sunglasses,sunglasses':
            // Sunglasses cover both eyes
            break;
        }
      }
    } catch (e) {
      switch (e.code) {
        // local error
        case TVErrorCode.UNAUTHORIZED:
        case TVErrorCode.NETWORK_ERROR:
        case TVErrorCode.INTERNAL_ERROR:
        case TVErrorCode.TIMEOUT_ERROR:
        case TVErrorCode.CANCELATION_ERROR:
          console.log('Error: ', e.code, ' - ', e.message);
          break;
        default:
          // error from backend
          console.log('Error: ', e.code, ' - ', e.message);

          switch (e.code) {
            // Id capturing
            case 'incorrect_card_type':
              // the input image is not same type with selected card
              break;
            case 'nocard_or_multicard_image':
              // the input image is no card or multicard detected
              break;

            // Selfie capturing
            case 'image_has_no_faces':
              // face not detected in selfie image
              break;
            case 'image_has_multipe_faces':
              // multiple faces are detected in selfie image
              break;

            // Face Matching
            case 'image_has_no_faces':
              // face not detected in selfie image
              break;
            case 'image_has_multipe_faces':
              // multiple faces are detected in selfie image
              break;

            // Common errors
            case 'access_denied_exception':
              break;
            case 'invalid_parameter_exception':
              break;
            case 'invalid_parameter_exception':
              break;
            case 'rate_limit_exception':
              break;
            case 'internal_server_error':
              break;
          }

          break;
      }
    }
  };

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <Button title={'Press Me'} onPress={onPress} />
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <Header />
          {global.HermesInternal == null ? null : (
            <View style={styles.engine}>
              <Text style={styles.footer}>Engine: Hermes</Text>
            </View>
          )}
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Step One</Text>
              <Text style={styles.sectionDescription}>
                Edit <Text style={styles.highlight}>App.js</Text> to change this
                screen and then come back to see your edits.
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>See Your Changes</Text>
              <Text style={styles.sectionDescription}>
                <ReloadInstructions />
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Debug</Text>
              <Text style={styles.sectionDescription}>
                <DebugInstructions />
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Learn More</Text>
              <Text style={styles.sectionDescription}>
                Read the docs to discover what to do next:
              </Text>
            </View>
            <LearnMoreLinks />
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
