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

import RNTrustVisionRnsdkFramework, {TVConst, TVErrorCode} from 'react-native-trust-vision-SDK';

const App: () => React$Node = () => {
  const onPress = async () => {
    try {
      await RNTrustVisionRnsdkFramework.initializeWithAcessKeyId(
        '5767c20d-87aa-4cad-8dbb-f5429f76c34b',
        'c1446919-e60a-4575-a05d-304318212a1b',
        true,
      );
      const cardTypes = await RNTrustVisionRnsdkFramework.getCardTypes();
      const config = {
        actionMode: TVConst.TVActionMode.LIVENESS,
        cardType: cardTypes[0],
        livenessMode: TVConst.TVLivenessMode.PASSIVE,
      };
      console.log('Config', config);
      const result = await RNTrustVisionRnsdkFramework.startFlowWithConfig(config);
      console.log('Result', result);
    } catch (e) {
      switch (e.code) {
        // local error
        case TVErrorCode.UNAUTHORIZED:
        case TVErrorCode.NETWORK_ERROR:
        case TVErrorCode.INTERNAL_ERROR:
        case TVErrorCode.TIMEOUT_ERROR:
            console.log('Error: ', e.code, ' - ', e.message);
            break;
        default:
          // error from backend
          console.log('Error: ', e.code, ' - ', e.message);
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
