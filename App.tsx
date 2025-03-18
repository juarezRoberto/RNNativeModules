import React, { useEffect } from 'react';
import type { PropsWithChildren } from 'react';
import {
  Button,
  ScrollView,
  StatusBar,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors
} from 'react-native/Libraries/NewAppScreen';

import { NativeModules, NativeEventEmitter } from 'react-native';
const { FirstModule, MyTalentsModule } = NativeModules
const eventEmitter = new NativeEventEmitter(FirstModule)
console.log(FirstModule)
FirstModule.createCounterEvent((res: string) => console.log(res + "plus"))


function App(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  useEffect(() => {
    eventEmitter.addListener('EventCount', count => {
      console.log(count)
    })

    return () => {
      eventEmitter.removeAllListeners('EventCount');
    }
  }, [])

  const promiseExample = async () => {
    try {
      const result = await FirstModule.createCounterPromise()
      console.log(result)
    } catch (error) {
      console.log(error)
    }
  }

  const openModule = () => {
    MyTalentsModule.openModule()
  }

  const startServiceCoordinator = () => {
    MyTalentsModule.startServiceCoordinator()
  }

  return (
    <View style={backgroundStyle}>

      <ScrollView
        style={backgroundStyle}>
        <Button
          onPress={promiseExample}
          title="Run Javascript promise"
          color="green"
        />
        <View style={{ backgroundColor: 'blue', height: 30, width: 30 }} />
        <Button
          onPress={openModule}
          title="open module"
          color="blue"
        />
      </ScrollView>
    </View>
  );
}

export default App;
