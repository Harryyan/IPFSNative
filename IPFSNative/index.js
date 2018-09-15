import React from 'react';
import { AppRegistry, StyleSheet, Text, View, Button, NativeModules, ScrollView, Platform } from 'react-native';

export default class Home extends React.Component {

  constructor(props) {
    super(props);

    this.initialState = {
      shouldShowResult: false,
      hashValue: "",
      content: ""
    };
    
    this.state = this.initialState
  }

  componentDidMount() {
    this.setState({ 
      shouldShowResult: false,
      hashValue: "",
      content: ""
    });
  }

  onPressUpload() {
    // NativeModules.Upload.uploadFileWithCallBack( 
    //   (result) => {
    //     this.setState({ 
    //       shouldShowResult: true,
    //       hashValue: result.hashKey,
    //       content: result.contentKey
    //     });
    // })

    NativeModules.UploadFileModule.uploadFileWithCallBack((success)=>{alert(success)},(error)=>{alert(error)})
  }

  render() {
    return (
      <ScrollView style={styles.container}>
        <View style={styles.uploadArea}>
          <View style={styles.buttonContainer}>
            <Button uppercase={false} onPress={() => this.onPressUpload()} title="Upload following file to IPFS" {...Platform.select({ios: {color:'#FFFFFF'}, android:{color:'#2E9298'}})}/>
          </View>
          <Text style={styles.fileTitle}>Test.txt</Text>
        </View>
        <View style={styles.resultArea}>
          <Text style={{marginLeft: 16, fontSize: 18, textAlign: "left", color: this.state.shouldShowResult ? '#000000':'#ada8a8'}}>File hash on IPFS: </Text>
          <Text style={styles.hash}>{this.state.hashValue}</Text>
          <Text style={{marginLeft: 16, fontSize: 18, textAlign: "left", color: this.state.shouldShowResult ? '#000000':'#ada8a8'}}>File content on IPFS: </Text>
          <Text style={styles.content}>{this.state.content}</Text>
        </View>
      </ScrollView> 
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  uploadArea: {
    ...Platform.select({
      ios: {
        marginTop: 120
      },
      android: {
        marginTop: 60
      }
    }),
    justifyContent: 'center',
    alignItems:'center',
    flex: 1
  },
  resultArea: {
    flex: 2,
    alignItems:'flex-start',
  },
  buttonContainer: {
    ...Platform.select({
      ios: {
        backgroundColor: '#2E9298',
        borderRadius: 10,
        padding: 10,
        shadowColor: '#000000',
        shadowOffset: {
          width: 0,
          height: 3
        },
        shadowRadius: 10,
        shadowOpacity: 0.25,
      },
      android: {}
    })
  },
  fileTitle: {
    fontSize: 20,
    textAlign: 'center',
    fontStyle: 'italic',
    color: '#ada8a8',
    margin: 30,
  },
  hash: {
    fontSize: 18,
    textAlign: 'left',
    fontStyle: 'italic',
    color: '#ada8a8',
    margin: 30,
  },
  content: {
    fontSize: 16,
    textAlign: 'left',
    margin: 20,
    color: '#ada8a8'
  }
});

AppRegistry.registerComponent('Home', () => Home);