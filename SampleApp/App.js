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
      // id capturing
      const cardType = {
        id: 'card_id',
        name: 'card_name',
        orientation: TVConst.Orientation.LANDSCAPE,
        hasBackSide: true,
      };
      const idConfig = {
        cardType: cardType,
        isEnableSound: false,
        isReadBothSide: true,
        cardSide: TVConst.CardSide.FRONT,
      };
      console.log('Id Config', idConfig);
      const idResult = await RNTrustVisionRnsdkFramework.startIdCapturing(
        idConfig,
      );
      console.log('Id Result', idResult);

      // selfie capturing
      // const selfieConfig = {
      //   cameraOption: TVConst.SelfieCameraMode.FRONT,
      //   livenessMode: TVConst.LivenessMode.PASSIVE,
      //   isEnableSound: false,
      // };
      // console.log('Selfie Config', selfieConfig);
      // const selfieResult = await RNTrustVisionRnsdkFramework.startSelfieCapturing(
      //   selfieConfig,
      // );
      // console.log('Selfie Result', selfieResult);
    } catch (e) {
      console.log('Error: ', e.code, ' - ', e.message);
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
